import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Signup from './Signup/Signup';
import Login from './Login/Login';
import Header from './Header/Header';
import Profile from './Profile/Profile';

const App = () => {
  return (
    <Router>
      <Routes>
    
        <Route path="/user-signup" element={<Signup />} />
        <Route path="/user-login" element={<Login />} />
        <Route path="/home" element={<Header />} />
        <Route path="/user-profile" element={<><Header /> <Profile/></>} />
        
      </Routes>
    </Router>
  );
};

export default App;
