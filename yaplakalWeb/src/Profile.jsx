import { useEffect, useState } from "react";
import { Link, Navigate, useNavigate, useParams } from "react-router-dom";

export default function Profile() {
    const [content, setContent] = useState(null)
    const navigate = useNavigate()

    useEffect(()=>{
        fetch(`http://127.0.0.1:8080/profile`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"username":  localStorage.getItem("username"),
            "password":  localStorage.getItem("password")})
        })
        .then((resp)=>resp.json())
        .then((data)=>setContent(data))
    }, [])

    return (
        <div>
            <button onClick={()=>navigate("/editProfile")}>Редактировать профиль</button>
            
            {content && <div>
                    <div>{content.nickname}</div>
                    <div>{content.name}</div>
                    <div>{content.age}</div>
                </div>}
        </div>
    )
}