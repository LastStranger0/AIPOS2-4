import React, {useCallback, useEffect, useState} from "react";
import axios from "axios";
import {useDropzone} from "react-dropzone";
import {withRouter} from "react-router-dom";

const Image = () => {

    const [game, setGame] = useState([]);

    const fetchGames = () => {
        axios.get("http://localhost:8082/games").then(res => {
            console.log(res);
            setGame(res.data.data);
        })
    }

    useEffect(() => {
        fetchGames();
    }, []);

    return game.map((game, index) => {
            return (
                    <div key={index}>
                    {game.id ? (
                        <img src={`http://localhost:8082/games/${game.id}/mainImage`}/>) : null}
                    <h1>{game.name}</h1>
                    <p>{game.price}</p>
                    <MyDropzone id={game.id}/>
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
        axios.post(`http://localhost:8082/games/${id}/mainImage/upload`,
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

        // axios.post()
        // Do somethin  g with the files
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

export default withRouter(Image);
