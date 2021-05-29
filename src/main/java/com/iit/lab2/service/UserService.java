package com.iit.lab2.service;

import com.iit.lab2.bucket.BucketName;
import com.iit.lab2.filestore.FileStore;
import com.iit.lab2.persist.entity.User;
import com.iit.lab2.persist.repo.ReviewRepository;
import com.iit.lab2.persist.repo.UserRepository;
import com.iit.lab2.persist.request.UserRequest;
import com.iit.lab2.persist.response.RestException;
import com.iit.lab2.persist.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final FileStore fileStore;
    private final int PORT = 8082;

    @Autowired
    public UserService(UserRepository userRepository, ReviewRepository reviewRepository, FileStore fileStore) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.fileStore = fileStore;
    }

    public void create(UserRequest userRequest) throws RestException {
        User user = new User();
        user.copyAttribute(userRequest);
        user.setDate(LocalDate.now());
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Username is busy", "username");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Email is busy", "email");
        }
        userRepository.save(user);
        uploadMainImage(user.getId(), userRequest.getLinkImage());
        log.info("{} was created", user.getUsername());
    }

    public List<UserResponse> findAll() {
        Iterable<User> source = userRepository.findAll();
        List<User> target = new ArrayList<>();
        List<UserResponse> result = new ArrayList<>();
        source.forEach(target::add);
        for (int i = 0; i < target.size(); i++) {
            result.add(new UserResponse(target.get(i)));
        }
        log.info("{} addresses was found", target.size());
        return result;
    }

    public void delete(Long id) throws RestException {
        User user = findById(id).get();
        reviewRepository.deleteByAuthor(user);
        userRepository.deleteById(id);
        log.info("The user has been removed");
    }

    public Optional<User> findById(Long id) throws RestException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.info("User with id {} was found", id);
        } else {
            log.info("User with id {} wasn't found", id);
            throw new RestException(HttpStatus.NOT_FOUND, "User not found", "user");
        }
        return user;
    }

    public void update(UserRequest userRequest) throws RestException {
        User oldUser = userRepository.findById(userRequest.getId()).get();
        if (!userRequest.getUsername().equals(oldUser.getUsername())) {
            if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Username is busy", "username");
            }
        }
        if (!userRequest.getEmail().equals(oldUser.getEmail())) {
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Email is busy", "email");
            }
        }
        if (Objects.nonNull(userRequest.getUsername())) {
            oldUser.setUsername(userRequest.getUsername());
        }
        if (Objects.nonNull(userRequest.getEmail())) {
            oldUser.setEmail(userRequest.getEmail());
        }
        if (Objects.nonNull(userRequest.getPassword())) {
            oldUser.setPassword(userRequest.getPassword());
        }
//        setNotNull(, oldUser);
        userRepository.save(oldUser);
        if (Objects.nonNull(userRequest.getLinkImage())) {
            uploadMainImage(userRequest.getId(), userRequest.getLinkImage());
        }
        log.info("{} has been updated", userRequest.getUsername());
    }

    private void setNotNull(User userOne, User userTwo) {
        if ("".equals(userOne.getUsername())) {
            userOne.setUsername(userTwo.getUsername());
        }
        if ("".equals(userOne.getEmail())) {
            userOne.setEmail(userTwo.getEmail());
        }
        if ("".equals(userOne.getPassword())) {
            userOne.setPassword(userTwo.getPassword());
        }
        if (Objects.isNull(userOne.getDate())) {
            userOne.setDate(userTwo.getDate());
        }
    }

    public void uploadMainImage(Long id, MultipartFile file) {
        isFileEmpty(file);
        isImage(file);
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Game not found"));
        Map<String, String> metadata = extractMetadata(file);
        String path = String.format("%s/%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), "users", user.getId());
        String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setLinkImage(fileName);
            userRepository.save(user);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private void isImage(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file[" + file.getSize() + "]");
        }
    }

    public byte[] downloadMainImage(Long id) throws RestException {
        User user = findById(id).get();
        String path = String.format("%s/%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                "users",
                user.getId());
        return user.getLinkImage().map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }
}
