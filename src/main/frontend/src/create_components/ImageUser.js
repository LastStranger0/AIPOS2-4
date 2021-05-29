import React, {useCallback, useEffect, useState} from "react";
import axios from "axios";
import {useDropzone} from "react-dropzone";
import {withRouter} from "react-router-dom";

const ImageUser = () => {

    const [users, setGame] = useState([]);

    const fetchUsers = () => {
        axios.get("http://localhost:8082/users").then(res => {
            console.log(res);
            setGame(res.data.data);
        })
    }

    useEffect(() => {
        fetchUsers();
    }, []);

    return users.map((users, index) => {
            return (
                <div key={index}>
                    {users.id ? (
                        <img src={users.linkImage}/>) : null}
                    <h1>{users.username}</h1>
                    <p>{users.email}</p>
                    <MyDropzone id={users.id}/>
                </div>
            );
        }
    );
};

function MyDropzone({id}) {
    const onDrop = useCallback(acceptedFiles => {
        const file = acceptedFiles[0];
        console.log(file);
        const formData = new FormData();
        formData.append("file", file);
        axios.post(`http://localhost:8082/user/${id}/mainImage/upload`,
            formData,
            {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            }
        ).then(() => {
            console.log("file uploaded successfully")
        }).catch(err => {
            console.log(err);
        });

    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

    return (
        <div {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the files here ...</p> :
                    <p>Drag 'n' drop some files here, or click to select files</p>
            }
        </div>
    )
}

export default withRouter(ImageUser);
