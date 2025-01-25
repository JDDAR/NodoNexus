import React, { useState } from "react";
import { login } from "../../../services/auth.service";

const SigninIn = () => {
  const [username, setUsername] = useState(""); // Estado para el usuario
  const [password, setPassword] = useState(""); // Estado para la contraseña
  const [error, setError] = useState(""); // Estado para errores

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault(); // Evitamos el comportamiento por defecto del formulario

    try {
      // Llamamos al servicio de login
      const token = await login(username, password);
      console.log("Inicio de sesión exitoso. Token:", token);

      // Aquí puedes redirigir al usuario o realizar otras acciones necesarias
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
