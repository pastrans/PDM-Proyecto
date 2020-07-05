package sv.edu.ues.fia.eisi.parcial3prueba;

public class Libro {

    private int idLibro;
    private String titulo;
    private String autor;
    private String categoria;
    private double precio;

    public Libro(int idLibro, String titulo, String autor, String categoria, double precio) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.precio = precio;
    }

    public Libro() {
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString(){
        return "ID: " + this.getIdLibro() + "\nTítulo: " + this.getTitulo() + "\nAutor: " + this.getAutor();
    }

}
