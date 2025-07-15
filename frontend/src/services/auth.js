import axios from 'axios';

const API_URL = '/auth';
// const API_URL = 'http://localhost:8080/auth';

const register = (username, password) => {
  return axios.post(`${API_URL}/register`, {
    username,
    password
  });
};

const login = async (username, password) => {
  const response = await axios.post('/auth/login', {
    username,
    password
  });
  
  if (response.data) {
    localStorage.setItem('user', JSON.stringify({
      username,
      role: response.data.role // Assuming your backend returns role in response
    }));
  }
  return response.data;
};

const logout = () => {
  localStorage.removeItem('user');
};


const handleOAuth2Redirect = async () => {
    try {
        const response = await axios.get(`${API_URL}/oauth2/success`, {
            withCredentials: true
        });
        
        if (response.data) {
            const userData = {
                username: response.data.username,
                role: response.data.role
            };
            localStorage.setItem('user', JSON.stringify(userData));
            return userData;
        }
        throw new Error('Authentication failed');
    } catch (error) {
        localStorage.removeItem('user');
        throw error;
    }
};


const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem('user'));
};

const authService = {
  register,
  login,
  logout,
  getCurrentUser,
  handleOAuth2Redirect
};

export default authService;
