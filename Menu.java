public class Menu {
    private String nama;
    private double harga;
    private String kategori; // Makanan atau Minuman
    private int id; // ID unik untuk setiap menu

    public Menu(int id, String nama, double harga, String kategori) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Setter methods (untuk mengubah harga)
    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%.0f) - %s", id, nama, harga, kategori);
    }
}

// Kelas untuk merepresentasikan item pesanan pelanggan
class PesananItem {
    private Menu menu;
    private int jumlah;

    public PesananItem(Menu menu, int jumlah) {
        this.menu = menu;
        this.jumlah = jumlah;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getTotalHargaItem() {
        return menu.getHarga() * jumlah;
    }
}
