import { useEffect, useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import './Posts.css'
import Header from "./Header";

function Posts () {
    const [posts, setPosts] = useState(null)
    const navigate = useNavigate()

    function fi() {
        fetch('http://127.0.0.1:8080/sort/date', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "username": localStorage.getItem("username"),
            "password": localStorage.getItem("password")
        }),
    }, {mode: 'no-cors'})
    .then((response)=>response.json())
    .then((data)=>setPosts(data))
    }

    function del(id) {
        fetch(`http://127.0.0.1:8080/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                "username": localStorage.getItem("username"),
                "password": localStorage.getItem("password")
            }),
        }, {mode: 'no-cors'})
    }

    function search(nick) {
        fetch(`http://127.0.0.1:8080/sort/nick/${nick}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "username": localStorage.getItem("username"),
            "password": localStorage.getItem("password")
        }),
    }, {mode: 'no-cors'})
    .then((response)=>response.json())
    .then((data)=>setPosts(data));
    }

    useEffect(()=>{
        fetch('http://127.0.0.1:8080/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "username": localStorage.getItem("username"),
            "password": localStorage.getItem("password")
        }),
    }, {mode: 'no-cors'})
    .then((response)=>response.json())
    .then((data)=>setPosts(data));
    }, [])

    return (
        <>
        <Header />
        <div className="filter-panel">
            <button onClick={()=>navigate("/createPost")}>Create post</button>
            <button onClick={()=>fi()}>sort by nick</button>
            <input type="text" onInput={(e)=>search(e.target.value)}/>
        </div>
        
        <div className="posts">
            {posts && posts.map(item=>
            
            <div key={item.id} className="post">
                    <Link to={`/post/${item.id}`}>
                        <span className="post__nickname">От: {item.nickname}</span>
                    </Link>
                <button onClick={()=>del(item.id)}>delete</button>
            </div>
            )}
        </div>
        </>
        )
}

export default Posts