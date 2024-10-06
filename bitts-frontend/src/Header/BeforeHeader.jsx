import React from 'react';
import './Header.css';

const Header = () => {
  return (
    <>
      <header className="header"> 
        <div className="logo">
          <img src="../Logo/darkthor.png" alt="Logo" />
          Bitts
        </div>
        <nav>
          <a href="#home">Home</a>
          <a href="#posts">Posts</a>
          <a href="#message">Message</a>
          <a href="#group">Groups</a>
          <a href="#profile">Profile</a>
          <a href="#more">More</a>
        </nav>
        <div className="cta-wrapper">
          <div className="cta">Login</div>
          <div className="cta">Signup</div>
        </div>
      </header>
    </>
  );
};

export default Header;
