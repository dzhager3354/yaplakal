import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Posts from './Posts.jsx'
import Auth from './Auth.jsx'
import CreatePost from './CreatePost.jsx'
import Post from './Post.jsx'
import Answer from './Answer.jsx'
import Profile from './Profile.jsx'
import EditProfile from './EditProfile.jsx'
import Registration from './Registration.jsx'

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        index: true,
        element: <Auth/>
      },
      {
        path: '/posts',
        element: <Posts/>
      },
      {
        path: '/createPost',
        element: <CreatePost />
      },
      {
        path: '/post/:id',
        element: <Post/>
      },
      {
        path: '/answer/:id',
        element: <Answer/>
      },
      {
        path: '/profile',
        element: <Profile/>
      },
      {
        path: '/registration',
        element: <Registration />
      },
      {
        path: '/editProfile',
        element: <EditProfile/>
      }
    ]
  }
])

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>,
)
