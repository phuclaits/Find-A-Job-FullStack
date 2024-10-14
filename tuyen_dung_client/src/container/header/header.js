import React, { useEffect, useState } from "react";
import { Link, NavLink } from "react-router-dom";
import "./header.css";
import "./header.scss";

const Header = () => {
  const [user, setUser] = useState(null);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isMobile, setIsMobile] = useState(false);

  const handleResize = () => {
    setIsMobile(window.innerWidth < 991);
  };

  useEffect(() => {
    const handleUserData = () => {
      const userData = JSON.parse(localStorage.getItem("userData"));
      // console.log("Current user:", userData);
      setUser(userData);
    };
    handleUserData();
    window.addEventListener("resize", handleResize);
    handleResize();
    return () => {
      window.removeEventListener("resize", handleResize);
      setUser(null);
    };
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("userData");
    localStorage.removeItem("token_user");
    setUser(null);
    window.location.href = "/login";
  };

  const toggleDropdown = () => {
    setIsDropdownOpen(!isDropdownOpen);
  };

  return (
    <header className="header-area bg-light shadow-sm">
      <div className="container-fluid">
        <nav className="navbar navbar-expand-lg navbar-light bg-transparent">
          <NavLink className="navbar-brand" to="/home">
            <img
              src="/assets/img/logo.png"
              style={{ maxHeight: "50px", maxWidth: "250px" }}
              alt="Logo"
            />
          </NavLink>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded={isMenuOpen ? "true" : "false"}
            aria-label="Toggle navigation"
            onClick={() => setIsMenuOpen(!isMenuOpen)}
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className={`collapse navbar-collapse ${isMenuOpen ? "show" : ""}`} id="navbarNav">
            <ul className="navbar-nav mx-auto">
              <li className="nav-item mx-3">
                <NavLink className="nav-link" to="/home">Trang chủ</NavLink>
              </li>
              <li className="nav-item mx-3">
                <NavLink className="nav-link" to="/job">Việc làm</NavLink>
              </li>
              <li className="nav-item mx-3">
                <NavLink className="nav-link" to="/company">Công ty</NavLink>
              </li>
              <li className="nav-item mx-3">
                <NavLink className="nav-link" to="/about">Giới thiệu</NavLink>
              </li>
              <li className="nav-item mx-3">
                <NavLink className="nav-link" to="/swaggerAPI">Tài Liệu API</NavLink>
              </li>
            </ul>

            <ul className="navbar-nav ms-auto auth-buttons">
              {user ? (
                <li className="nav-item dropdown">
                  <a className="nav-link dropdown-toggle" href="#" id="userDropdown" onClick={toggleDropdown}>
                    <img src={user.image} alt="User" className="rounded-circle" style={{ width: "30px", height: "30px" }} />
                    {user.firstName + " " + user.lastName + " "}
                    <i className="fas fa-chevron-down ms-2"></i>
                  </a>
                  <ul className={`dropdown-menu dropdown-menu-end ${isDropdownOpen ? "show" : ""}`} aria-labelledby="userDropdown">
                    <li><Link className="dropdown-item" to="/candidate/info"><i className="far fa-user text-primary"></i> Thông tin</Link></li>
                    <li><Link className="dropdown-item" to="/candidate/usersetting"><i className="fas fa-cogs text-primary"></i> Cài đặt</Link></li>
                    <li><Link className="dropdown-item" to="/candidate/cv-post"><i className="fas fa-file-alt text-primary"></i> CV đã nộp</Link></li>
                    <li><Link className="dropdown-item" to="/candidate/changepassword"><i className="fas fa-key text-primary"></i> Đổi mật khẩu</Link></li>
                    <li><button className="dropdown-item" onClick={handleLogout}><i className="fas fa-sign-out-alt text-primary"></i> Đăng xuất</button></li>
                  </ul>
                </li>
              ) : (
                <>
                  <li className="nav-item mx-3">
                    <Link to="/register" className={`nav-link ${isMobile ? 'nav-link text-center' : 'nav-link btn btn-primary text-center'}`}>Đăng ký</Link>
                  </li>
                  <li className="nav-item mx-3">
                    <Link to="/login" className={`nav-link ${isMobile ? 'nav-link text-center' : 'nav-link btn btn-secondary text-center'}`}>Đăng nhập</Link>
                  </li>
                </>
              )}
            </ul>
          </div>
        </nav>
      </div>
    </header>
  );
};

export default Header;