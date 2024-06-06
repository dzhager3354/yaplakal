import { useState } from "react";
import { Navigate, redirect } from "react-router-dom";

export default function CreatePost() {
    const [text, setText] = useState("")
    const [isPublish, setPublish] = useState(false)

    function sendPost() {
        fetch('http://127.0.0.1:8080/createPost', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                "user": {
                    "username": localStorage.getItem("username"),
                    "password": localStorage.getItem("password")
                },
                "post": {
                    "content": text
                }
            }),
        }, {mode: 'no-cors'}
        ).then(res=>setPublish(res.status==200))
    }

    return (
        <div>
            <input onInput={(event)=>setText(event.target.value)}></input>
            <button onClick={sendPost}></button>
            {isPublish ? <Navigate to="/posts"/> : "uncorrect"}
        </div>
    );
}