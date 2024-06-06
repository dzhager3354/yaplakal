import { useNavigate } from "react-router-dom"

export default function Header() {
    const navigate = useNavigate()
    return (
        <header>
            <span>{localStorage.getItem("username")}</span>
            <button onClick={()=> navigate("/profile")}>Профиль</button>
        </header>
    )
}