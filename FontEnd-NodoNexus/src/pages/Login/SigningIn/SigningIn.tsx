import React, { useState } from "react";
import { login } from "../../../services/auth.service";
import { useDispatch } from "react-redux";
import { AppDispatch } from "../../../redux/store";
import { setUser } from "../../../redux/states/userSlice";

const SigninIn = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const dispatch = useDispatch<AppDispatch>();

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault(); // Evitamos el comportamiento por defecto del formulario

    try {
      const userData = await login(username, password);
      dispatch(setUser(userData));
      console.log("Inicio de sesión exitoso. Datos de usuario:", userData);
      alert("Sesión iniciada correctamente...");
    } catch (error) {
      setError(
        "Error al iniciar sesión. Por favor, verifica tus credenciales." +
          error,
      );
    }
  };

  return (
    <div>
      <h1>Iniciar Sesión</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Usuario</label>
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Ingresa tu usuario"
            required
          />
        </div>
        <div>
          <label>Contraseña</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Ingresa tu contraseña"
            required
          />
        </div>
        {error && <p style={{ color: "red" }}>{error}</p>}
        <button type="submit">Iniciar Sesión</button>
      </form>
    </div>
  );
};

export default SigninIn;
