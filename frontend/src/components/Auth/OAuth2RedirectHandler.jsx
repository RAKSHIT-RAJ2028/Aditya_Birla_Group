import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
// import authService from '../services/auth';
import authService from '../../services/auth';

function OAuth2RedirectHandler() {
    const navigate = useNavigate();

    useEffect(() => {
        authService.handleOAuth2Redirect()
            .then(user => {
                if (!user || !user.role) {
                    throw new Error('Invalid user data');
                }
                
                // Normalize role comparison (backend might return ROLE_ADMIN/ROLE_USER)
                if (user.role.includes('ADMIN')) {
                    navigate('/admin-dashboard');
                } else {
                    navigate('/user-dashboard');
                }
            })
            .catch(error => {
                console.error('OAuth2 redirect error:', error);
                navigate('/login', { 
                    state: { 
                        error: error.message || 'Login failed' 
                    } 
                });
            });
    }, [navigate]);

    return <div>Redirecting...</div>;
}

export default OAuth2RedirectHandler;