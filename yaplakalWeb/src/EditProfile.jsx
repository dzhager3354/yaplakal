import { useEffect, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";
import Header from "./Header";

export default function EditProfile() {
    const [age,setAge] = useState(0)
    const [nick, setNick] = useState("")
    const navigate = useNavigate()

    useEffect(()=> {
        fetch(`http://127.0.0.1:8080/profile`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"username":  localStorage.getItem("username"),
            "password":  localStorage.getItem("password")})
        })
        .then((resp)=>resp.json())
        .then((data)=>{
            if (data == null) {
                setAge(0)
                setNick("")
            } else {
                setAge(data.age)
                setNick(data.name)
            }
        })
    }, [])

    function sendEdit() {
        if (nick === "") return
        fetch(`http://127.0.0.1:8080/updateProfile`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"user":{"username":  localStorage.getItem("username"),
            "password":  localStorage.getItem("password")}, "dto": {"age": age, "nick": nick}})
        })
        .then(resp=>{
            if (resp.status == 200) {
                navigate("/profile")
            }
        })
    }

    return (
        <div>
            <input type="text" onInput={(event)=>setNick(event.target.value)} value={nick}></input>
            <input type="number" onInput={(event)=>setAge(event.target.value)} value={age}></input>
            <button onClick={()=>sendEdit()}>edit</button>
        </div>
    )
}