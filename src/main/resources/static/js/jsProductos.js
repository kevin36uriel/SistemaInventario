document.addEventListener('DOMContentLoaded', () => {
    // Elementos del DOM
    const productoForm = document.getElementById("productoForm");
    const prestamoForm = document.getElementById("prestamoForm");
    const usuarioForm = document.getElementById("usuarioForm");
    const categoriaForm = document.getElementById("categoriaForm");
    const categoriaSelect = document.getElementById("categoria");
    const areaSelect = document.getElementById("area");
    const areaUsuarioSelect = document.getElementById("areaUsuario");
    const productoPrestamoSelect = document.getElementById("productoPrestamo");
    const tablaProductos = document.getElementById("tablaProductos");
    const tablaPrestamos = document.getElementById("tablaPrestamos");
    const tablaUsuarios = document.getElementById("tablaUsuarios");
    const tablaCategorias = document.getElementById("tablaCategorias");
    const buscarProductos = document.getElementById("buscarProductos");
    const buscarPrestamos = document.getElementById("buscarPrestamos");
    const buscarUsuarios = document.getElementById("buscarUsuarios");
    const buscarCategorias = document.getElementById("buscarCategorias");
    const filtroCategoria = document.getElementById("filtroCategoria");
    const filtroEstado = document.getElementById("filtroEstado");
    const showProductosBtn = document.getElementById("showProductos");
    const showPrestamosBtn = document.getElementById("showPrestamos");
    const showUsuariosBtn = document.getElementById("showUsuarios");
    const showCategoriasBtn = document.getElementById("showCategorias");
    const productosSection = document.getElementById("productosSection");
    const prestamosSection = document.getElementById("prestamosSection");
    const usuariosSection = document.getElementById("usuariosSection");
    const categoriasSection = document.getElementById("categoriasSection");
    const modal = document.getElementById("modalConfirm");
    const modalTitle = document.getElementById("modalTitle");
    const modalMessage = document.getElementById("modalMessage");
    const modalConfirmBtn = document.getElementById("modalConfirmBtn");
    const modalCancelBtn = document.getElementById("modalCancelBtn");
    const closeModalBtn = document.querySelector(".close-modal");
    const carreraUsuarioSelect = document.getElementById("carreraUsuario");

    // Verificar elementos del DOM
    if (!productoForm) console.warn('Elemento productoForm no encontrado');
    if (!prestamoForm) console.warn('Elemento prestamoForm no encontrado');
    if (!usuarioForm) console.warn('Elemento usuarioForm no encontrado');
    if (!categoriaForm) console.warn('Elemento categoriaForm no encontrado');
    if (!categoriaSelect) console.warn('Elemento categoria no encontrado');
    if (!areaSelect) console.warn('Elemento area no encontrado');
    if (!areaUsuarioSelect) console.warn('Elemento areaUsuario no encontrado');
    if (!productoPrestamoSelect) console.warn('Elemento productoPrestamo no encontrado');
    if (!tablaProductos) console.warn('Elemento tablaProductos no encontrado');
    if (!tablaPrestamos) console.warn('Elemento tablaPrestamos no encontrado');
    if (!tablaUsuarios) console.warn('Elemento tablaUsuarios no encontrado');
    if (!tablaCategorias) console.warn('Elemento tablaCategorias no encontrado');
    if (!buscarProductos) console.warn('Elemento buscarProductos no encontrado');
    if (!buscarPrestamos) console.warn('Elemento buscarPrestamos no encontrado');
    if (!buscarUsuarios) console.warn('Elemento buscarUsuarios no encontrado');
    if (!buscarCategorias) console.warn('Elemento buscarCategorias no encontrado');
    if (!filtroCategoria) console.warn('Elemento filtroCategoria no encontrado');
    if (!filtroEstado) console.warn('Elemento filtroEstado no encontrado');
    if (!showProductosBtn) console.warn('Elemento showProductosBtn no encontrado');
    if (!showPrestamosBtn) console.warn('Elemento showPrestamosBtn no encontrado');
    if (!showUsuariosBtn) console.warn('Elemento showUsuariosBtn no encontrado');
    if (!showCategoriasBtn) console.warn('Elemento showCategoriasBtn no encontrado');
    if (!productosSection) console.warn('Elemento productosSection no encontrado');
    if (!prestamosSection) console.warn('Elemento prestamosSection no encontrado');
    if (!usuariosSection) console.warn('Elemento usuariosSection no encontrado');
    if (!categoriasSection) console.warn('Elemento categoriasSection no encontrado');
    if (!modal) console.warn('Elemento modalConfirm no encontrado');
    if (!modalTitle) console.warn('Elemento modalTitle no encontrado');
    if (!modalMessage) console.warn('Elemento modalMessage no encontrado');
    if (!modalConfirmBtn) console.warn('Elemento modalConfirmBtn no encontrado');
    if (!modalCancelBtn) console.warn('Elemento modalCancelBtn no encontrado');
    if (!closeModalBtn) console.warn('Elemento close-modal no encontrado');
    if (!carreraUsuarioSelect) console.warn('Elemento carreraUsuario no encontrado');

    let productos = [];
    let prestamos = [];
    let usuarios = [];
    let categorias = [];
    let areasUsuario = [];
    let editarProductoId = null;
    let editarPrestamoId = null;
    let editarUsuarioNombre = null;
    let editarUsuarioApellidos = null;
    let editarCategoriaNombre = null;

    function toYYYYMMDD(value) {
        if (!value) return '';
        if (typeof value === 'string' && /^\d{4}-\d{2}-\d{2}$/.test(value)) return value;
        const d = new Date(value); if (isNaN(d)) return '';
        return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')}`;
    }

    // Controlar visibilidad de botones según rol
    function controlarVisibilidadBotones() {
        const rol = localStorage.getItem('rol');
        console.log('Rol del usuario:', rol);
        if (rol === 'ADMIN' || rol === 'JEFECARRERA') {
            if (showUsuariosBtn) showUsuariosBtn.style.display = 'inline-block';
            if (showCategoriasBtn) showCategoriasBtn.style.display = 'inline-block';
        } else {
            if (showUsuariosBtn) showUsuariosBtn.style.display = 'none';
            if (showCategoriasBtn) showCategoriasBtn.style.display = 'none';
        }
        if (showProductosBtn) showProductosBtn.style.display = 'inline-block';
        if (showPrestamosBtn) showPrestamosBtn.style.display = 'inline-block';
    }

    // Cargar categorías desde backend
    async function cargarCategorias() {
        try {
            console.log('Iniciando carga de categorías...');
            const response = await fetch('/Categoria/GetAllCategoria');
            if (!response.ok) {
                throw new Error(`Error al cargar categorías: ${response.status} ${response.statusText}`);
            }
            categorias = await response.json();
            console.log('Categorías cargadas:', categorias);

            if (categoriaSelect) {
                categoriaSelect.innerHTML = '<option value="">Seleccione</option>';
                categorias.forEach(cat => {
                    const option = document.createElement('option');
                    option.value = cat.idCategoria;
                    option.textContent = cat.nombreCategoria;
                    categoriaSelect.appendChild(option);
                });
                console.log('Selector de categorías actualizado:', categoriaSelect.innerHTML);
            }

            if (filtroCategoria) {
                filtroCategoria.innerHTML = '<option value="">Todas las categorías</option>';
                categorias.forEach(cat => {
                    const option = document.createElement('option');
                    option.value = cat.idCategoria;
                    option.textContent = cat.nombreCategoria;
                    filtroCategoria.appendChild(option);
                });
                console.log('Selector de filtro de categorías actualizado:', filtroCategoria.innerHTML);
            }

            renderTablaCategorias();
        } catch (error) {
            console.error('Error al cargar categorías:', error);
            alert('Error al cargar categorías: ' + error.message);
        }
    }

    // Cargar áreas para productos basadas en la carrera del usuario desde localStorage
    async function cargarAreasUsuario() {
        try {
            const carrera = localStorage.getItem('carrera');
            console.log('Carrera desde localStorage:', carrera);
            if (!carrera) {
                console.warn('No se encontró carrera en localStorage');
                alert('No se encontró carrera en localStorage. Por favor, inicia sesión nuevamente.');
                if (areaSelect) areaSelect.innerHTML = '<option value="">Seleccione</option>';
                return;
            }

            console.log('Cargando áreas para carrera:', carrera);
            const response = await fetch(`/Areas/GetUserAreas?carrera=${encodeURIComponent(carrera)}`);
            if (!response.ok) {
                throw new Error(`Error al cargar áreas: ${response.status} ${response.statusText}`);
            }
            areasUsuario = await response.json();
            console.log('Áreas cargadas:', areasUsuario);

            if (areaSelect) {
                areaSelect.innerHTML = '<option value="">Seleccione</option>';
                areasUsuario.forEach(ar => {
                    const option = document.createElement('option');
                    option.value = ar;
                    option.textContent = ar;
                    areaSelect.appendChild(option);
                });
                console.log('Selector de áreas (productos) actualizado:', areaSelect.innerHTML);
            }
        } catch (error) {
            console.error('Error al cargar áreas:', error);
            alert('Error al cargar áreas: ' + error.message);
        }
    }

    // Cargar áreas para el formulario de usuario basado en la carrera seleccionada
    async function cargarAreasPorCarrera(carrera) {
        try {
            console.log('Cargando áreas para carrera seleccionada:', carrera);
            if (!carrera) {
                if (areaUsuarioSelect) {
                    areaUsuarioSelect.innerHTML = '<option value="">Seleccione</option>';
                }
                return;
            }
            const response = await fetch(`/Areas/GetAreasByCarrera?carrera=${encodeURIComponent(carrera)}`);
            if (!response.ok) {
                throw new Error(`Error al cargar áreas para la carrera ${carrera}: ${response.status} ${response.statusText}`);
            }
            const areas = await response.json();
            console.log('Áreas cargadas para carrera:', areas);

            if (areaUsuarioSelect) {
                areaUsuarioSelect.innerHTML = '<option value="">Seleccione</option>';
                areas.forEach(ar => {
                    const option = document.createElement('option');
                    option.value = ar;
                    option.textContent = ar;
                    areaUsuarioSelect.appendChild(option);
                });
                console.log('Selector de áreas (usuarios) actualizado:', areaUsuarioSelect.innerHTML);
            }
        } catch (error) {
            console.error('Error al cargar áreas para la carrera:', error);
            alert('Error al cargar áreas para la carrera: ' + error.message);
        }
    }

    // Cargar productos desde backend
    async function cargarProductos() {
        try {
            console.log('Iniciando carga de productos...');
            const response = await fetch('/Productos/GetAllProductos');
            if (!response.ok) {
                throw new Error(`Error al cargar productos: ${response.status} ${response.statusText}`);
            }
            const todosProductos = await response.json();
            console.log('Productos totales:', todosProductos);

            productos = todosProductos.filter(p => areasUsuario.includes(p.area));
            console.log('Productos filtrados por área:', productos);
            renderTablaProductos();
            cargarProductosEnPrestamoSelect();
        } catch (error) {
            console.error('Error al cargar productos:', error);
            alert('Error al cargar productos: ' + error.message);
        }
    }

    // Cargar préstamos desde backend
    async function cargarPrestamos() {
        try {
            console.log('Iniciando carga de préstamos...');
            const response = await fetch('/Prestamo/GetAllPrestamo');
            if (!response.ok) {
                throw new Error(`Error al cargar préstamos: ${response.status} ${response.statusText}`);
            }
            prestamos = await response.json();
            console.log('Préstamos cargados:', prestamos);
            renderTablaPrestamos();
        } catch (error) {
            console.error('Error al cargar préstamos:', error);
            alert('Error al cargar préstamos: ' + error.message);
        }
    }

    // Cargar usuarios desde backend
    async function cargarUsuarios() {
        try {
            console.log('Iniciando carga de usuarios...');
            const response = await fetch('/Empleado/GetAllEmpleado');
            if (!response.ok) {
                throw new Error(`Error al cargar usuarios: ${response.status} ${response.statusText}`);
            }
            usuarios = await response.json();
            console.log('Usuarios cargados:', usuarios);
            renderTablaUsuarios();
        } catch (error) {
            console.error('Error al cargar usuarios:', error);
            alert('Error al cargar usuarios: ' + error.message);
        }
    }

    // Cargar productos en el select de préstamos
    function cargarProductosEnPrestamoSelect() {
        if (productoPrestamoSelect) {
            productoPrestamoSelect.innerHTML = '<option value="">Seleccione</option>';
            productos.forEach(p => {
                const option = document.createElement('option');
                option.value = p.idProducto;
                option.textContent = `${p.nombre} (Disponible: ${p.cantidadDisponible})`;
                productoPrestamoSelect.appendChild(option);
            });
            console.log('Selector de productos para préstamo actualizado:', productoPrestamoSelect.innerHTML);
        }
    }

    // Obtener nombre de categoría por id
    function obtenerNombreCategoria(idCategoria) {
        const cat = categorias.find(c => c.idCategoria == idCategoria);
        return cat ? cat.nombreCategoria : '-';
    }

    // Obtener nombre de producto por id
    function obtenerNombreProducto(idProducto) {
        const prod = productos.find(p => p.idProducto == idProducto);
        return prod ? prod.nombre : '-';
    }

    // Verificar si el producto pertenece al área del usuario
    function esProductoDeAreaUsuario(idProducto) {
        return productos.some(p => p.idProducto == idProducto);
    }

    // Renderizar tabla de productos
    function renderTablaProductos() {
        if (tablaProductos) {
            tablaProductos.innerHTML = "";
            const searchTerm = buscarProductos ? buscarProductos.value.toLowerCase() : '';
            const catFilter = filtroCategoria ? filtroCategoria.value : '';
            productos
                .filter(p => p.nombre.toLowerCase().includes(searchTerm) && (!catFilter || p.idCategoria == catFilter))
                .forEach((p, index) => {
                    const fila = `
                    <tr>
                        <td>${p.nombre}</td>
                        <td>${obtenerNombreCategoria(p.idCategoria)}</td>
                        <td>${p.area}</td>
                        <td>${p.cantidadDisponible}</td>
                        <td>${p.descripcion}</td>
                        <td>${p.codigo}</td>
                        <td>
                            <button class="btn-action btn-edit" onclick="editarProducto(${index})">Editar</button>
                            <button class="btn-action btn-delete" onclick="eliminarProducto(${index})">Eliminar</button>
                        </td>
                    </tr>`;
                    tablaProductos.innerHTML += fila;
                });
            console.log('Tabla de productos renderizada con', productos.length, 'elementos');
        }
    }

    // Renderizar tabla de préstamos
    function renderTablaPrestamos() {
        if (tablaPrestamos) {
            tablaPrestamos.innerHTML = "";
            const searchTerm = buscarPrestamos ? buscarPrestamos.value.toLowerCase() : '';
            const estadoFilter = filtroEstado ? filtroEstado.value : '';
            prestamos
                .filter(p => obtenerNombreProducto(p.producto) !== '-' &&
                        (p.alumno.toLowerCase().includes(searchTerm) || p.noControl.toLowerCase().includes(searchTerm)) &&
                        (!estadoFilter || p.estado === estadoFilter))
                .forEach((p, index) => {
                    const fila = `
                    <tr>
                        <td>${obtenerNombreProducto(p.producto)}</td>
                        <td>${p.alumno}</td>
                        <td>${p.noControl}</td>
                        <td>${p.cantidad}</td>
                        <td>${toYYYYMMDD(p.fechaPrestamo)}</td>
                        <td>${p.fechaDevolucion ? toYYYYMMDD(p.fechaDevolucion) : '-'}</td>
                        <td><span class="status-badge ${String(p.estado).toLowerCase()}">${p.estado}</span></td>
                        <td>
                            <button class="btn-action btn-edit" onclick="editarPrestamo(${index})">Editar</button>
                            <button class="btn-action btn-delete" onclick="eliminarPrestamo(${index})">Eliminar</button>
                        </td>
                    </tr>`;
                    tablaPrestamos.innerHTML += fila;
                });
            console.log('Tabla de préstamos renderizada con', prestamos.length, 'elementos');
        }
    }

    // Renderizar tabla de usuarios
    function renderTablaUsuarios() {
        if (tablaUsuarios) {
            tablaUsuarios.innerHTML = "";
            const searchTerm = buscarUsuarios ? buscarUsuarios.value.toLowerCase() : '';
            usuarios
                .filter(u => (u.nombre.toLowerCase().includes(searchTerm) || u.apellidos.toLowerCase().includes(searchTerm)))
                .forEach((u, index) => {
                    const fila = `
                    <tr>
                        <td>${u.nombre}</td>
                        <td>${u.apellidos}</td>
                        <td>${u.rol}</td>
                        <td>${u.carrera}</td>
                        <td>${u.area}</td>
                        <td>${u.telefono || '-'}</td>
                        <td>${u.email || '-'}</td>
                        <td>${u.usuario?.usuario || '-'}</td>
                        <td>
                            <button class="btn-action btn-edit" onclick="editarUsuario(${index})">Editar</button>
                            <button class="btn-action btn-delete" onclick="eliminarUsuario(${index})">Eliminar</button>
                        </td>
                    </tr>`;
                    tablaUsuarios.innerHTML += fila;
                });
            console.log('Tabla de usuarios renderizada con', usuarios.length, 'elementos');
        }
    }

    // Renderizar tabla de categorías
    function renderTablaCategorias() {
        if (tablaCategorias) {
            tablaCategorias.innerHTML = "";
            const searchTerm = buscarCategorias ? buscarCategorias.value.toLowerCase() : '';
            categorias
                .filter(c => c.nombreCategoria.toLowerCase().includes(searchTerm))
                .forEach((c, index) => {
                    const fila = `
                    <tr>
                        <td>${c.nombreCategoria}</td>
                        <td>
                            <button class="btn-action btn-edit" onclick="editarCategoria(${index})">Editar</button>
                            <button class="btn-action btn-delete" onclick="eliminarCategoria(${index})">Eliminar</button>
                        </td>
                    </tr>`;
                    tablaCategorias.innerHTML += fila;
                });
            console.log('Tabla de categorías renderizada con', categorias.length, 'elementos');
        }
    }

    // Guardar o actualizar producto
    if (productoForm) {
        productoForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const productoData = {
                nombre: document.getElementById("nombre").value,
                descripcion: document.getElementById("descripcion").value,
                codigo: document.getElementById("codigo").value,
                idCategoria: parseInt(categoriaSelect.value),
                area: areaSelect.value,
                cantidadDisponible: parseInt(document.getElementById("cantidadDisponible").value)
            };
            try {
                let response;
                if (editarProductoId) {
                    response = await fetch(`/Productos/UpdateProducto?id=${editarProductoId}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(productoData)
                    });
                } else {
                    response = await fetch('/Productos/SaveProducto', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(productoData)
                    });
                }
                if (response.ok) {
                    await cargarProductos();
                    productoForm.reset();
                    alert(editarProductoId ? "Producto actualizado correctamente" : "Producto guardado correctamente");
                    editarProductoId = null;
                } else {
                    const errorText = await response.text();
                    console.error('Error al guardar producto:', errorText);
                    alert('Error al guardar producto: ' + errorText);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
                alert('Error en la solicitud: ' + error.message);
            }
        });
    }

    if (prestamoForm) {
        prestamoForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const idUsuario = localStorage.getItem('idUsuario');
            if (!idUsuario) {
                alert('No se encontró el ID de usuario. Por favor, inicia sesión nuevamente.');
                return;
            }

            const prestamoData = {
                producto: parseInt(document.getElementById("productoPrestamo").value),
                alumno: document.getElementById("alumno").value,
                noControl: document.getElementById("noControl").value,
                usuario: parseInt(idUsuario),
                cantidad: parseInt(document.getElementById("cantidadPrestamo").value),
                fechaPrestamo: document.getElementById("fechaPrestamo").value || null,    // YYYY-MM-DD
                fechaDevolucion: document.getElementById("fechaDevolucion").value || null,// YYYY-MM-DD
                estado: document.getElementById("estado").value
            };

            try {
                let response;
                if (editarPrestamoId) {
                    response = await fetch(`/Prestamo/UpdatePrestamo?id=${editarPrestamoId}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(prestamoData)
                    });
                } else {
                    response = await fetch('/Prestamo/SavePrestamo', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(prestamoData)
                    });
                }

                if (response.ok) {
                    await cargarPrestamos();
                    await cargarProductos();
                    prestamoForm.reset();
                    // re-habilita inputs por si quedaron deshabilitados de un “Editar”
                    document.getElementById("productoPrestamo").disabled = false;
                    document.getElementById("cantidadPrestamo").disabled = false;

                    alert(editarPrestamoId ? "Préstamo actualizado correctamente" : "Préstamo guardado correctamente");
                    editarPrestamoId = null;
                } else {
                    const errorText = await response.text();
                    console.error('Error al guardar préstamo:', errorText);
                    alert('Error al guardar préstamo: ' + errorText);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
                alert('Error en la solicitud: ' + error.message);
            }
        });
    }


    // Guardar o actualizar usuario
    if (usuarioForm) {
        usuarioForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const usuarioData = {
                nombre: document.getElementById("nombreUsuario").value,
                apellidos: document.getElementById("apellidosUsuario").value,
                rol: document.getElementById("rolUsuario").value,
                carrera: document.getElementById("carreraUsuario").value,
                area: document.getElementById("areaUsuario").value,
                telefono: document.getElementById("telefonoUsuario").value || null,
                email: document.getElementById("emailUsuario").value || null,
                usuario: {
                    usuario: document.getElementById("usuarioNombre").value,
                    password: document.getElementById("passwordUsuario").value,
                    rol: document.getElementById("rolUsuario").value,
                    carrera: document.getElementById("carreraUsuario").value,
                    area: document.getElementById("areaUsuario").value
                }
            };
            try {
                let response;
                if (editarUsuarioNombre && editarUsuarioApellidos) {
                    response = await fetch(`/Empleado/ActualizaEmpleado?nombre=${encodeURIComponent(editarUsuarioNombre)}&apellidos=${encodeURIComponent(editarUsuarioApellidos)}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(usuarioData)
                    });
                } else {
                    response = await fetch('/Empleado/Enviar', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(usuarioData)
                    });
                }
                if (response.ok) {
                    await cargarUsuarios();
                    usuarioForm.reset();
                    if (carreraUsuarioSelect) carreraUsuarioSelect.value = '';
                    if (areaUsuarioSelect) areaUsuarioSelect.innerHTML = '<option value="">Seleccione</option>';
                    alert((editarUsuarioNombre && editarUsuarioApellidos) ? "Usuario actualizado correctamente" : "Usuario guardado correctamente");
                    editarUsuarioNombre = null;
                    editarUsuarioApellidos = null;
                } else {
                    const errorText = await response.text();
                    console.error('Error al guardar usuario:', errorText);
                    alert('Error al guardar usuario: ' + errorText);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
                alert('Error en la solicitud: ' + error.message);
            }
        });
    }

    // Guardar o actualizar categoría
    if (categoriaForm) {
        categoriaForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const categoriaData = {
                nombreCategoria: document.getElementById("nombreCategoria").value
            };
            try {
                let response;
                if (editarCategoriaNombre) {
                    response = await fetch(`/Categoria/UpdateCategoria?nombreCategoria=${encodeURIComponent(editarCategoriaNombre)}`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(categoriaData)
                    });
                } else {
                    response = await fetch('/Categoria/SaveCategoria', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify(categoriaData)
                    });
                }
                if (response.ok) {
                    await cargarCategorias();
                    categoriaForm.reset();
                    alert(editarCategoriaNombre ? "Categoría actualizada correctamente" : "Categoría guardada correctamente");
                    editarCategoriaNombre = null;
                } else {
                    const errorText = await response.text();
                    console.error('Error al guardar categoría:', errorText);
                    alert('Error al guardar categoría: ' + errorText);
                }
            } catch (error) {
                console.error('Error en la solicitud:', error);
                alert('Error en la solicitud: ' + error.message);
            }
        });
    }

// --- ELIMINAR PRODUCTO (única versión correcta) ---
window.eliminarProducto = (index) => {
  const p = productos[index];

  if (modalTitle && modalMessage && modal) {
    modalTitle.textContent = 'Confirmar Eliminación';
    modalMessage.textContent = `¿Seguro que quieres eliminar ${p.nombre}?`;
    modal.style.display = 'flex';
  }

  modalConfirmBtn.onclick = async () => {
    try {
      const usuarioId  = localStorage.getItem('idUsuario');   // requerido por el backend
      const empleadoId = localStorage.getItem('idEmpleado');  // puede ser null

      if (!usuarioId) {
        alert('No se encontró idUsuario en el navegador. Inicia sesión de nuevo.');
        return;
      }

      const motivo = prompt('Escribe el motivo de baja del producto:');
      if (motivo === null || motivo.trim() === '') {
        alert('Debes ingresar un motivo.');
        return;
      }

      // Construye la URL con usuarioId obligatorio y empleadoId si existe
      let url = `/Productos/DeleteProducto`
              + `?id=${encodeURIComponent(p.idProducto)}`
              + `&motivo=${encodeURIComponent(motivo)}`
              + `&usuarioId=${encodeURIComponent(usuarioId)}`;

      if (empleadoId && empleadoId !== 'null' && empleadoId !== '') {
        url += `&empleadoId=${encodeURIComponent(empleadoId)}`;
      }

      console.log('[DEL URL]', url);

      const response = await fetch(url, { method: 'DELETE' });

      if (!response.ok) {
        const errorText = await response.text();
        alert('No se pudo eliminar el producto: ' + errorText);
        return;
      }

      // Descargar PDF si el backend lo envía
      const ct = response.headers.get('content-type') || '';
      if (ct.includes('application/pdf')) {
        const blob = await response.blob();
        const blobUrl = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = blobUrl;
        a.download = `Baja_${(p.nombre || 'producto').replace(/[^\w\-]+/g, '_')}.pdf`;
        document.body.appendChild(a);
        a.click();
        a.remove();
        URL.revokeObjectURL(blobUrl);
      }

      await cargarProductos();
      if (modal) modal.style.display = 'none';
    } catch (err) {
      console.error('Error al eliminar:', err);
      alert('Error al eliminar: ' + err.message);
    }
  };
};






    // Eliminar préstamo (por idPrestamo)
    window.eliminarPrestamo = (index) => {
        const p = prestamos[index];
        if (modalTitle && modalMessage && modal) {
            modalTitle.textContent = 'Confirmar Eliminación';
            modalMessage.textContent = `¿Seguro que quieres eliminar el préstamo de ${p.alumno}?`;
            modal.style.display = 'flex';
        }
        modalConfirmBtn.onclick = async () => {
            try {
                // Ajusta este endpoint si tu backend expone otra ruta para borrar por id
                const response = await fetch(`/Prestamo/DeletePrestamo?id=${p.idPrestamo}`, {
                    method: 'DELETE'
                });
                if (response.ok) {
                    await cargarPrestamos();
                    await cargarProductos();
                    modal.style.display = 'none';
                } else {
                    const errorText = await response.text();
                    console.error('Error al eliminar préstamo:', errorText);
                    alert('No se pudo eliminar el préstamo: ' + errorText);
                }
            } catch (error) {
                console.error('Error al eliminar:', error);
                alert('Error al eliminar: ' + error.message);
            }
        };
    };

    // Eliminar usuario
    window.eliminarUsuario = (index) => {
        const u = usuarios[index];
        if (modalTitle && modalMessage && modal) {
            modalTitle.textContent = 'Confirmar Eliminación';
            modalMessage.textContent = `¿Seguro que quieres eliminar al usuario ${u.nombre} ${u.apellidos}?`;
            modal.style.display = 'flex';
        }
        modalConfirmBtn.onclick = async () => {
            try {
                const response = await fetch(`/Empleado/BorrarEmpleado?nombre=${encodeURIComponent(u.nombre)}&apellidos=${encodeURIComponent(u.apellidos)}`, {
                    method: 'DELETE'
                });
                if (response.ok) {
                    await cargarUsuarios();
                    modal.style.display = 'none';
                } else {
                    const errorText = await response.text();
                    console.error('Error al eliminar usuario:', errorText);
                    alert('No se pudo eliminar el usuario: ' + errorText);
                }
            } catch (error) {
                console.error('Error al eliminar:', error);
                alert('Error al eliminar: ' + error.message);
            }
        };
    };

    // Eliminar categoría
    window.eliminarCategoria = (index) => {
        const c = categorias[index];
        if (modalTitle && modalMessage && modal) {
            modalTitle.textContent = 'Confirmar Eliminación';
            modalMessage.textContent = `¿Seguro que quieres eliminar la categoría ${c.nombreCategoria}?`;
            modal.style.display = 'flex';
        }
        modalConfirmBtn.onclick = async () => {
            try {
                const response = await fetch(`/Categoria/DeleteCategoria?nombreCategoria=${encodeURIComponent(c.nombreCategoria)}`, {
                    method: 'DELETE'
                });
                if (response.ok) {
                    await cargarCategorias();
                    modal.style.display = 'none';
                } else {
                    const errorText = await response.text();
                    console.error('Error al eliminar categoría:', errorText);
                    alert('No se pudo eliminar la categoría: ' + errorText);
                }
            } catch (error) {
                console.error('Error al eliminar:', error);
                alert('Error al eliminar: ' + error.message);
            }
        };
    };

    // Editar producto
    window.editarProducto = (index) => {
        const p = productos[index];
        document.getElementById("nombre").value = p.nombre;
        document.getElementById("descripcion").value = p.descripcion;
        document.getElementById("codigo").value = p.codigo;
        document.getElementById("categoria").value = p.idCategoria;
        document.getElementById("area").value = p.area;
        document.getElementById("cantidadDisponible").value = p.cantidadDisponible;
        editarProductoId = p.idProducto;
    };

    // Editar préstamo (AHORA guarda idPrestamo y normaliza fechas)
    window.editarPrestamo = (index) => {
        const p = prestamos[index];
        // 1. Refrescar el select para garantizar opciones actualizadas
        cargarProductosEnPrestamoSelect();

        const select = document.getElementById("productoPrestamo");
        const productIdStr = String(p.producto);

        // 2. Intentar seleccionar la opción normal
        select.value = productIdStr;
        let found = false;
        for (let option of select.options) {
            if (option.value === productIdStr) {
                option.selected = true;
                found = true;
                break;
            }
        }

        // 3. Si no se encontró (producto eliminado o fuera de área), agregar opción temporal
        if (!found) {
            const nombreProducto = obtenerNombreProducto(p.producto);
            const tempOption = document.createElement('option');
            tempOption.value = productIdStr;
            tempOption.textContent = `${nombreProducto} (Editando - ya no disponible)`;
            tempOption.selected = true;
            select.appendChild(tempOption);
            console.warn('Producto no encontrado en lista actual, se agregó temporalmente:', p.producto);
        }

        // 4. Deshabilitar campos que no se pueden cambiar
        select.disabled = true;
        document.getElementById("cantidadPrestamo").value = p.cantidad;
        document.getElementById("cantidadPrestamo").disabled = true;

        // 5. Rellenar el resto
        document.getElementById("alumno").value = p.alumno;
        document.getElementById("noControl").value = p.noControl;
        document.getElementById("fechaPrestamo").value = toYYYYMMDD(p.fechaPrestamo);
        document.getElementById("fechaDevolucion").value = p.fechaDevolucion ? toYYYYMMDD(p.fechaDevolucion) : '';
        document.getElementById("estado").value = p.estado;

        editarPrestamoId = p.idPrestamo;
    };

    // Editar usuario
    window.editarUsuario = (index) => {
        const u = usuarios[index];
        document.getElementById("nombreUsuario").value = u.nombre;
        document.getElementById("apellidosUsuario").value = u.apellidos;
        document.getElementById("rolUsuario").value = u.rol;
        document.getElementById("carreraUsuario").value = u.carrera;
        document.getElementById("telefonoUsuario").value = u.telefono || '';
        document.getElementById("emailUsuario").value = u.email || '';
        document.getElementById("usuarioNombre").value = u.usuario?.usuario || '';
        document.getElementById("passwordUsuario").value = ''; // No se muestra la contraseña
        cargarAreasPorCarrera(u.carrera).then(() => {
            document.getElementById("areaUsuario").value = u.area;
        });
        editarUsuarioNombre = u.nombre;
        editarUsuarioApellidos = u.apellidos;
    };

    // Editar categoría
    window.editarCategoria = (index) => {
        const c = categorias[index];
        document.getElementById("nombreCategoria").value = c.nombreCategoria;
        editarCategoriaNombre = c.nombreCategoria;
    };

    // Escuchar cambios en el select de carrera para actualizar áreas
    if (carreraUsuarioSelect) {
        carreraUsuarioSelect.addEventListener('change', (e) => {
            const carrera = e.target.value;
            cargarAreasPorCarrera(carrera);
        });
    }

    // Cambiar entre secciones
    if (showProductosBtn) {
        showProductosBtn.addEventListener('click', async () => {
            productosSection.style.display = 'flex';
            prestamosSection.style.display = 'none';
            usuariosSection.style.display = 'none';
            categoriasSection.style.display = 'none';
            showProductosBtn.classList.add('active');
            showPrestamosBtn.classList.remove('active');
            if (showUsuariosBtn) showUsuariosBtn.classList.remove('active');
            if (showCategoriasBtn) showCategoriasBtn.classList.remove('active');
            await cargarProductos();
            gsap.from("#productosSection .fade-in", { opacity: 0, y: 50, duration: 1, stagger: 0.2 });
        });
    }

    if (showPrestamosBtn) {
        showPrestamosBtn.addEventListener('click', async () => {
            prestamosSection.style.display = 'flex';
            productosSection.style.display = 'none';
            usuariosSection.style.display = 'none';
            categoriasSection.style.display = 'none';
            showPrestamosBtn.classList.add('active');
            showProductosBtn.classList.remove('active');
            if (showUsuariosBtn) showUsuariosBtn.classList.remove('active');
            if (showCategoriasBtn) showCategoriasBtn.classList.remove('active');
            // Refrescar datos al entrar a la sección para evitar cache
            await cargarPrestamos();
            await cargarProductos();
            cargarProductosEnPrestamoSelect();
            renderTablaPrestamos();
            gsap.from("#prestamosSection .fade-in", { opacity: 0, y: 50, duration: 1, stagger: 0.2 });
        });
    }

    if (showUsuariosBtn) {
        showUsuariosBtn.addEventListener('click', async () => {
            usuariosSection.style.display = 'flex';
            productosSection.style.display = 'none';
            prestamosSection.style.display = 'none';
            categoriasSection.style.display = 'none';
            showUsuariosBtn.classList.add('active');
            showProductosBtn.classList.remove('active');
            showPrestamosBtn.classList.remove('active');
            if (showCategoriasBtn) showCategoriasBtn.classList.remove('active');
            await cargarUsuarios();
            gsap.from("#usuariosSection .fade-in", { opacity: 0, y: 50, duration: 1, stagger: 0.2 });
        });
    }

    if (showCategoriasBtn) {
        showCategoriasBtn.addEventListener('click', async () => {
            categoriasSection.style.display = 'flex';
            productosSection.style.display = 'none';
            prestamosSection.style.display = 'none';
            usuariosSection.style.display = 'none';
            showCategoriasBtn.classList.add('active');
            showProductosBtn.classList.remove('active');
            showPrestamosBtn.classList.remove('active');
            if (showUsuariosBtn) showUsuariosBtn.classList.remove('active');
            await cargarCategorias();
            gsap.from("#categoriasSection .fade-in", { opacity: 0, y: 50, duration: 1, stagger: 0.2 });
        });
    }

    // Buscar y filtrar
    if (buscarProductos) buscarProductos.addEventListener("input", renderTablaProductos);
    if (filtroCategoria) filtroCategoria.addEventListener("change", renderTablaProductos);
    if (buscarPrestamos) buscarPrestamos.addEventListener("input", renderTablaPrestamos);
    if (filtroEstado) filtroEstado.addEventListener("change", renderTablaPrestamos);
    if (buscarUsuarios) buscarUsuarios.addEventListener("input", renderTablaUsuarios);
    if (buscarCategorias) buscarCategorias.addEventListener("input", renderTablaCategorias);

    // Cerrar modal
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', () => {
            if (modal) modal.style.display = 'none';
        });
    }
    if (modalCancelBtn) {
        modalCancelBtn.addEventListener('click', () => {
            if (modal) modal.style.display = 'none';
        });
    }
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && modal) modal.style.display = 'none';
    });

    // Inicializar
    controlarVisibilidadBotones();
    (async () => {
        await cargarCategorias();
        await cargarAreasUsuario();
        await cargarProductos();
        await cargarPrestamos();
        if (localStorage.getItem('rol') === 'ADMIN' || localStorage.getItem('rol') === 'JEFECARRERA') {
            await cargarUsuarios();
        }
    })();
});