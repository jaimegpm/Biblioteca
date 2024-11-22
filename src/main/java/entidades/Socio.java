package entidades;

public class Socio {
    private int idsocio;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;
    private String clave;  // La contraseña encriptada
    private String version;

    // Constructor con todos los campos
    public Socio(int idsocio, String nombre, String email, String direccion, String telefono, String clave) {
        this.idsocio = idsocio;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.clave = clave;
    }

    // Constructor vacío (por si se necesita)
    public Socio() {
    }

    // Getters y setters
    public int getIdsocio() {
        return idsocio;
    }

    public void setIdsocio(int idsocio) {
        this.idsocio = idsocio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
