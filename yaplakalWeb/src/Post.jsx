import { useEffect, useState } from "react";
import { Navigate, redirect, useParams } from "react-router-dom"
import './Post.css'
import Header from "./Header";


export default function Post() {
    const {id} = useParams()
    const [data, setData] = useState(null)
    const [answers, setAnswers] = useState(null)
    const [click, setClick] = useState(false)
    const [count, setCount] = useState(0)
    
    useEffect(()=> {
        fetch(`http://127.0.0.1:8080/post/${id}`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"username":  localStorage.getItem("username"),
            "password":  localStorage.getItem("password")})
        })
        .then((item)=>item.json())
        .then((dat)=>{
            setData(dat)
        })
        fetch(`http://127.0.0.1:8080/answers/${id}`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({"username":  localStorage.getItem("username"),
            "password":  localStorage.getItem("password")})
        }).then((item)=>item.json())
            .then((dat)=>setAnswers(dat))
        
        fetch(`http://127.0.0.1:8080/count/${id}`, {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({"username":  localStorage.getItem("username"),
                "password":  localStorage.getItem("password")})
            }).then((resp)=>resp.json())
            .then(data=>setCount(data))
    }, [])

    return (
        <>
        <Header/>
        <div className="post-container">
            <button onClick={()=>setClick(true)}>Ответить</button>
            <span>count answers: {count}</span>
            <div className="original-post">
                <span>От:{data && data.nickname}</span>
                <span>{data && data.content}</span>
            </div>
            {answers &&
            answers.map((item)=><div className="answer" key={item.id}>
                <span>Ответ от:{item.author} - </span>
                <span>{item.content}</span>
                </div>)}
        {click ? <Navigate to={`/answer/${id}`}/> : null}
        </div>
        </>
        
    )
}