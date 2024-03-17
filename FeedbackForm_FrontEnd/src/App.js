import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";

import ViewAllUser from "./components/ViewEmployees/ViewAllUser";
import LoginForm from "./components/Authentication/LoginForm";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import UpdateUser from "./components/Add/UpdateUser";
import FeedbackView from "./components/Feedback/FeedbackView";
import FeedbackForm from "./components/Feedback/FeedbackForm";
import LogoutButton from "./components/Navbar/Navbarlogon";
import AddUser from "./components/Add/AddUser";
import { useNavigate, useLocation } from "react-router-dom";

import { useState, useEffect } from "react";
import PrivateRoute from "./PrivateRoute";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(() => {
    const storedAuthState = sessionStorage.getItem("isAuthenticated");
    return storedAuthState ? JSON.parse(storedAuthState) : false;
  });

  useEffect(() => {
    sessionStorage.setItem("isAuthenticated", JSON.stringify(isAuthenticated));
  }, [isAuthenticated]);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const isLoginPage = location.pathname === "/";

    if (isLoginPage) {
      sessionStorage.clear();
      setIsAuthenticated(false);
    }
  }, [location.pathname]);

  const handleLogin = (value) => {
    setIsAuthenticated(value);
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
  };

  return (
    <>
      {isAuthenticated && <LogoutButton onLogout={handleLogout} />}
      <Routes>
        <Route path="/" element={<LoginForm onLogin={handleLogin} />} />
        <Route path="/:userId/:name/:uniqueString" element={<FeedbackForm />} />
        <Route
          path="/addUser"
          element={
            <PrivateRoute
              isAuthenticated={isAuthenticated}
              element={<AddUser />}
            />
          }
        />
        <Route
          path="/viewAllUser"
          element={
            <PrivateRoute
              isAuthenticated={isAuthenticated}
              element={<ViewAllUser />}
            />
          }
        />
        <Route
          path="/updateUser/:userId"
          element={
            <PrivateRoute
              isAuthenticated={isAuthenticated}
              element={<UpdateUser />}
            />
          }
        />
        <Route
          path="/readFeedback/:userId"
          element={
            <PrivateRoute
              isAuthenticated={isAuthenticated}
              element={<FeedbackView />}
            />
          }
        />
      </Routes>
    </>
  );
}

export default App;
