import { useState } from "react"
import { useNavigate } from "react-router-dom"

export default function Registration() {
    const [login, setLogin] = useState("")
    const [password, setPassword] = useState("")
    const navigate = useNavigate()

    function reg() {
        if (login === "" || password === "") return;
        fetch(`http://127.0.0.1:8080/registerUser`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"username":  login,
            "password":  password})
        })
        .then((resp)=>{
            if (resp.status == 200) {
                localStorage.setItem("username", login)
                localStorage.setItem("password", password)
                navigate("/posts")
            }
        })
    }

    return (
        <div>
            <input onInput={(event)=>setLogin(event.target.value)}/>
            <input onInput={(event)=>setPassword(event.target.value)}/>
            <button onClick={()=>reg()}>ЗАРЕГИСТРИРОВАТЬСЯ</button>
        </div>
    )
}