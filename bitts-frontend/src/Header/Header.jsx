import React, { useState } from 'react';
import logo from '../Logo/darkthor.png';
import './Header.css';

const Header = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  return (
    <>
      <header className="header"> 
        <div className="logo">
          <img src={logo} alt="Logo" width={50} height={50}/>
          Bitts
        </div>
        <nav>
          <a href="#home">Home</a>
          <a href="#posts">Posts</a>
          <a href="#message">Message</a>
          <a href="#group">Groups</a>
          <a href="user-profile">Profile</a>
        </nav>
        <div className="cta-wrapper">
          <div className="cta" onClick={toggleDropdown}>
            More
          </div>
          {dropdownOpen && (
            <div className="dropdown">
              <a href="#settings">Settings</a>
              <a href="#help">Help</a>
              <a href="#logout">Logout</a>
            </div>
          )}
        </div>
      </header>
    </>
  );
};

export default Header;
