import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';

const Profile = () => {
  const [profile, setProfile] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [updatedProfile, setUpdatedProfile] = useState({});

  useEffect(() => {
    // Fetch profile data from the backend
    axios.get('/api/profile')
      .then(response => {
        setProfile(response.data);
        setUpdatedProfile(response.data);
      })
      .catch(error => console.error('Error fetching profile data:', error));
  }, []);

  const handleEditClick = () => {
    setIsEditing(!isEditing);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedProfile(prevState => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSaveClick = () => {
    axios.put('/api/profile', updatedProfile)
      .then(response => {
        setProfile(response.data);
        setIsEditing(false);
      })
      .catch(error => console.error('Error updating profile:', error));
  };

  if (!profile) return <div>Loading...</div>;

  return (
    <div className="profile-container">
      <div className="profile-header">
        <img src={profile.photo ? `data:image/jpeg;base64,${profile.photo}` : '/default-avatar.png'} alt="Profile" className="profile-photo" />
        <h1>{profile.name}</h1>
      </div>
      <div className="profile-details">
        <div className="profile-bio">
          <h2>Bio</h2>
          {isEditing ? (
            <textarea
              name="bio"
              value={updatedProfile.bio || ''}
              onChange={handleChange}
            />
          ) : (
            <p>{profile.bio}</p>
          )}
        </div>
        <div className="profile-skills">
          <h2>Skills</h2>
          {isEditing ? (
            <input
              type="text"
              name="skills"
              value={updatedProfile.skills?.join(', ') || ''}
              onChange={handleChange}
            />
          ) : (
            <p>{profile.skills?.join(', ')}</p>
          )}
        </div>
        <div className="profile-links">
          <h2>Links</h2>
          <p>LinkedIn: <a href={profile.linkedinProfile} target="_blank" rel="noopener noreferrer">{profile.linkedinProfile}</a></p>
          <p>GitHub: <a href={profile.codingProfile} target="_blank" rel="noopener noreferrer">{profile.codingProfile}</a></p>
          <p>Project Profile: <a href={profile.projectProfile} target="_blank" rel="noopener noreferrer">{profile.projectProfile}</a></p>
        </div>
      </div>
      <div className="profile-actions">
        <button onClick={handleEditClick}>{isEditing ? 'Cancel' : 'Edit'}</button>
        {isEditing && <button onClick={handleSaveClick}>Save</button>}
      </div>
    </div>
  );
};

export default Profile;
