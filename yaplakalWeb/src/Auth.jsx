import { useState } from "react"
import { Navigate, redirect, redirectDocument, useNavigate } from "react-router-dom";
import './Auth.css'

function Auth() {
    const [login, setLogin] = useState(localStorage.getItem("username") || "")
    const [password, setPassword] = useState(localStorage.getItem("password") || "")
    const [isAuth, setAuth] = useState(undefined)
    const navigate = useNavigate()

    function tryAuth() {
        fetch('http://127.0.0.1:8080/auth', {
            method:"POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"username": login, "password": password})
        })
        .then((res)=>{
            if (res.status==200) {
                localStorage.setItem("username", login)
                localStorage.setItem("password", password)
            }
            setAuth(res.status==200);
        });
    }

    return(
        <div className="auth">
            Авторизация
            <input className="mar" type="text" value={login} onInput={(event)=>setLogin(event.target.value)}></input>
            <input className="mar" type="text" value={password} onInput={(event)=>setPassword(event.target.value)}></input>
            <button className="mar" onClick={()=>tryAuth()}>Log in</button>
            <button className="mar" onClick={()=>navigate("/registration")}>РЕГИСТРАЦИЯ</button>
            {isAuth != undefined ? (isAuth ? <Navigate to={"/posts"}/> : "Неверный логин или пароль") : ""}
        </div>
    )
}

export default Auth