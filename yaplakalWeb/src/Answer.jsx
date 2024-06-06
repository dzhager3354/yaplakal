import { useState } from "react"
import { Navigate, redirect, useNavigate, useParams } from "react-router-dom"

export default function Answer() {
    const {id} = useParams()
    const [message, setMessage] = useState("")
    const [click, setClick] = useState(false)
    const user = {"username":  localStorage.getItem("username"),
    "password":  localStorage.getItem("password")}
    const navigate = useNavigate()

    function sendAnswer() {
        fetch(`http://127.0.0.1:8080/answer`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"user": user, "dto": {"postId":id,"content": message}})
        })
        .then((resp)=> {
            if (resp.status == 200) {
                navigate(`/post/${id}`)
            }
        })
    }

    return (
        <div>
            <input onInput={(event)=>setMessage(event.target.value)}></input>
            <button onClick={()=>sendAnswer()}>Ответить</button>
            {click ? <Navigate to={`post/${id}`}></Navigate> : null}
        </div>
    )
}