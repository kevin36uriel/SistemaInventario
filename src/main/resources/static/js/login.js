    document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById("loginForm");
    if (!loginForm) {
        console.warn('Elemento loginForm no encontrado');
        return;
    }

    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const usuario  = document.getElementById("usuario").value;
        const password = document.getElementById("password").value;

        const formData = new URLSearchParams();
        formData.append("usuario", usuario);
        formData.append("password", password);

        try {
        const response = await fetch("/Tecnologico/Autenticar", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8" },
            body: formData
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}`);
        }

        const data = await response.json();
        console.log('Respuesta de /Tecnologico/Autenticar:', data);

        if (data.success) {
            // Siempre guarda idUsuario (requerido para eliminar)
            localStorage.setItem('idUsuario', String(data.idUsuario));

            // idEmpleado puede ser null; guarda "" para evitar "null" literal
            const idEmp = (data.idEmpleado === null || data.idEmpleado === undefined) ? "" : String(data.idEmpleado);
            localStorage.setItem('idEmpleado', idEmp);

            localStorage.setItem('carrera', data.edificio);
            localStorage.setItem('rol', data.rol);

            // Depuración rápida
            console.log('Guardado en localStorage:', {
            idUsuario: localStorage.getItem('idUsuario'),
            idEmpleado: localStorage.getItem('idEmpleado'),
            carrera: localStorage.getItem('carrera'),
            rol: localStorage.getItem('rol')
            });

            switch (data.edificio) {
            case "ISC":      window.location.href = "/Login/ISC"; break;
            case "IGE":      window.location.href = "/Login/IGE"; break;
            case "IA":       window.location.href = "/Login/IA"; break;
            case "II":       window.location.href = "/Login/II"; break;
            case "ALMACEN":  window.location.href = "/Login/ALMACEN"; break;
            default:
                document.getElementById("mensaje").innerText = "No se encontró el edificio para este usuario";
            }
        } else {
            // Limpia por si había residuos
            localStorage.removeItem('idUsuario');
            localStorage.removeItem('idEmpleado');
            localStorage.removeItem('carrera');
            localStorage.removeItem('rol');

            document.getElementById("mensaje").innerText = data.message || "Usuario o contraseña incorrectos";
        }
        } catch (error) {
        console.error("Error en autenticación:", error);
        document.getElementById("mensaje").innerText = "Error en autenticación: " + error.message;
        }
    });
    });
