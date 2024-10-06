import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Signup.css';

const Signup = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: ''
  });

  const [otpSent, setOtpSent] = useState(false);
  const [otp, setOtp] = useState('');
  const [otpValidated, setOtpValidated] = useState(false);
  const [error, setError] = useState('');
  const [timer, setTimer] = useState(0);

  const navigate = useNavigate();

  // Handle input changes
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  // Send OTP
  const sendOtp = async () => {
    try {
      await axios.post('http://localhost:8081/api/v1/user/otp-create', { email: formData.email });
      setOtpSent(true);
      setTimer(120); // 2-minute timer
      const countdown = setInterval(() => {
        setTimer(prev => {
          if (prev === 1) {
            clearInterval(countdown);
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
    } catch (error) {
      setError('Failed to send OTP. Try again.');
    }
  };

  // Validate OTP
  const validateOtp = async () => {
    try {
      const response = await axios.post('http://localhost:8081/api/v1/user/otp-validate', { email: formData.email, otp });
      if (response.status === 200) {
        setOtpValidated(true);
        setError('');
      } else {
        setError('Invalid OTP.');
      }
    } catch (error) {
      setError('OTP validation failed.');
    }
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!otpValidated) {
      setError('Please validate OTP first.');
      return;
    }

    try {
      const response = await axios.post('http://localhost:8888/api/v1/user/signup', formData);
      if (response.status === 200) {
        navigate('/uslogin');
      }
    } catch (error) {
      setError('Signup failed. Please try again.');
    }
  };

  // Handle timer logic
  useEffect(() => {
    if (timer === 0) return;
    const countdown = setInterval(() => {
      setTimer(prev => {
        if (prev === 1) {
          clearInterval(countdown);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    return () => clearInterval(countdown);
  }, [timer]);

  return (
    <div className="signup-container">
      <h2>Signup</h2>
      {error && <div className="error-message">{error}</div>}
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="firstName"
          placeholder="First Name"
          value={formData.firstName}
          onChange={handleChange}
          required
          className="input-field"
        />
        <input
          type="text"
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
          required
          className="input-field"
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
          className="input-field"
        />
        {!otpSent && (
          <button
            type="button"
            onClick={sendOtp}
            className="btn-send-otp"
            disabled={!formData.email}
          >
            Send OTP
          </button>
        )}
        
        {otpSent && (
          <>
            <div className="otp-container">
              <input
                type="text"
                name="otp"
                placeholder="Enter OTP"
                value={otp}
                onChange={(e) => setOtp(e.target.value)}
                className="input-field"
              />
              <button
                type="button"
                onClick={validateOtp}
                className="btn-validate-otp"
                disabled={otpValidated}
              >
                Validate OTP
              </button>
            </div>
            <div className="timer">
              {timer > 0 ? `Resend OTP in ${timer}s` : <button onClick={sendOtp}>Resend OTP</button>}
            </div>
          </>
        )}
        
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
          className="input-field"
        />
        
        <button
          type="submit"
          className="btn-signup"
          disabled={!otpValidated}
        >
          Signup
        </button>
      </form>
    </div>
  );
};

export default Signup;
