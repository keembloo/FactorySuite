import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../api/authApi';
import './Login.css';

function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await login(username, password);
      console.log(res.data);

      if (res.data.token) {
        localStorage.setItem('token', res.data.token);
        navigate('/items');
      } else {
        setError(res.data.error);
      }
    } catch (err) {
      console.error(err);
      setError('Server error');
    }
  };

  return (
      <div className="login-container">
        <h2>Login</h2>
        <form onSubmit={handleLogin}>
          <div>
            <input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div>
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {error && <p>{error}</p>}
          <button type="submit">Login</button>
        </form>
      </div>
    );
  }


export default Login;