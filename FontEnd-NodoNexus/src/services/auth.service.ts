const baseUrl = "http://localhost:9090/api/";
const loginUrl = baseUrl + "login";

export const login = async (userName: string, password: string) => {
  try {
    const response = await fetch(loginUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ userName, password }),
    });

    //Validamos la respuesta exitosa
    if (!response.ok) {
      throw new Error(`Error ${response.status}:${response.statusText}`);
    }

    //Obtenemos los datos en formato JSON
    const text = await response.text(); //Resivo el token como texto
    let data;
    try {
      data = JSON.parse(text);
    } catch {
      data = text;
    }
    const token = data.token || data;

    //Guardamos el tokenen localStorage
    localStorage.setItem("token", token);
    console.log("Token Obtenido: ", token);

    //Retornamos el token
    return data.token;
  } catch (error) {
    console.log("Error al iniciar sesión: ", error);
    throw error;
  }
};
