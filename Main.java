import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate; // Menggunakan java.time untuk tanggal
import java.time.LocalTime; // Menggunakan java.time untuk waktu
import java.time.format.DateTimeFormatter; // Untuk format waktu

public class Main {
    // Menggunakan ArrayList agar lebih dinamis dalam penambahan/penghapusan menu
    private static List<Menu> daftarMenu = new ArrayList<>();
    private static int nextMenuId = 1; // Untuk ID menu otomatis
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Inisialisasi menu awal (minimal 4 makanan, 4 minuman)
        inisialisasiMenuAwal();

        boolean running = true;
        // while loop untuk menu utama aplikasi
        while (running) {
            tampilkanMenuUtama();
            System.out.print("Pilih opsi: ");
            String pilihan = scanner.nextLine();

            // switch case untuk navigasi menu utama
            switch (pilihan) {
                case "1":
                    menuPelanggan();
                    break;
                case "2":
                    menuManajemen();
                    break;
                case "3":
                    running = false;
                    System.out.println("Terima kasih telah menggunakan aplikasi Dapoer Makassar!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        scanner.close(); // Menutup scanner setelah selesai menggunakan
    }

    // Method untuk inisialisasi menu awal
    private static void inisialisasiMenuAwal() {
        // Makanan
        tambahMenu(new Menu(nextMenuId++, "Nasi Goreng Merah", 25000, "Makanan"));
        tambahMenu(new Menu(nextMenuId++, "Coto Makassar", 38000, "Makanan"));
        tambahMenu(new Menu(nextMenuId++, "Pallumara Ikan Kakap", 60000, "Makanan"));
        tambahMenu(new Menu(nextMenuId++, "Konro Bakar Saus Kacang Hitam", 45000, "Makanan"));
        tambahMenu(new Menu(nextMenuId++, "Mie Titi", 28000, "Makanan"));

        // Minuman
        tambahMenu(new Menu(nextMenuId++, "Es Pisang Ijo", 15000, "Minuman"));
        tambahMenu(new Menu(nextMenuId++, "Barongko Pisang", 20000, "Minuman"));
        tambahMenu(new Menu(nextMenuId++, "Sarabba", 10000, "Minuman"));
        tambahMenu(new Menu(nextMenuId++, "Es Teh Tawar", 5000, "Minuman"));
        tambahMenu(new Menu(nextMenuId++, "Kopi Susu", 12000, "Minuman"));
    }

    // Method untuk menambah menu ke daftar
    private static void tambahMenu(Menu menu) {
        daftarMenu.add(menu);
    }

    // Method untuk menampilkan menu utama aplikasi
    private static void tampilkanMenuUtama() {
        System.out.println("\n=== Dapoer Makassar ===");
        System.out.println("1. Pesan Makanan/Minuman (Pelanggan)");
        System.out.println("2. Manajemen Menu (Pemilik Restoran)");
        System.out.println("3. Keluar");
    }

    // --- Bagian Menu Pelanggan ---
    private static void menuPelanggan() {
        List<PesananItem> pesananPelanggan = new ArrayList<>();
        System.out.println("\n=== Selamat Datang di Dapoer Makassar ===");
        System.out.println("Silakan pesan menu favorit Anda. Ketik 'selesai' untuk mengakhiri pesanan.");

        boolean memesan = true;
        // while loop untuk menerima pesanan pelanggan
        while (memesan) {
            tampilkanDaftarMenu();
            System.out.print("Masukkan ID menu yang ingin dipesan (atau 'selesai'): ");
            String input = scanner.nextLine();

            // Struktur keputusan if-else untuk mengakhiri pesanan atau memproses input
            if (input.equalsIgnoreCase("selesai")) {
                memesan = false;
            } else {
                try {
                    int menuId = Integer.parseInt(input);
                    Menu chosenMenu = null;
                    // Menggunakan for-each loop untuk mencari menu berdasarkan ID
                    for (Menu menu : daftarMenu) {
                        if (menu.getId() == menuId) {
                            chosenMenu = menu;
                            break;
                        }
                    }

                    // Struktur keputusan if-else if untuk memvalidasi menu yang dipilih
                    if (chosenMenu != null) {
                        int jumlah = 0;
                        boolean jumlahValid = false;
                        // do-while loop untuk validasi jumlah pesanan
                        do {
                            try {
                                System.out.print("Masukkan jumlah pesanan untuk " + chosenMenu.getNama() + ": ");
                                jumlah = Integer.parseInt(scanner.nextLine());
                                if (jumlah > 0) {
                                    jumlahValid = true;
                                } else {
                                    System.out.println("Jumlah harus lebih dari 0. Silakan coba lagi.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Input jumlah tidak valid. Harap masukkan angka.");
                            }
                        } while (!jumlahValid);

                        pesananPelanggan.add(new PesananItem(chosenMenu, jumlah));
                        System.out.println(jumlah + " " + chosenMenu.getNama() + " ditambahkan ke pesanan.");

                    } else {
                        System.out.println("ID menu tidak ditemukan. Silakan coba lagi.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input tidak valid. Harap masukkan ID menu (angka) atau 'selesai'.");
                }
            }
        }

        // Struktur keputusan if untuk mengecek apakah ada pesanan
        if (!pesananPelanggan.isEmpty()) {
            hitungDanCetakStruk(pesananPelanggan);
        } else {
            System.out.println("Tidak ada pesanan dibuat. Kembali ke menu utama.");
        }
    }

    // Method untuk menampilkan daftar menu yang tersedia, dikelompokkan berdasarkan kategori
    private static void tampilkanDaftarMenu() {
        System.out.println("\n--- Daftar Menu Dapoer Makassar ---");
        System.out.println("Makanan:");
        // Menggunakan for-each loop untuk menampilkan menu makanan
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equalsIgnoreCase("Makanan")) {
                System.out.println(String.format("  [%d] %-20s Rp %,8.0f", menu.getId(), menu.getNama(), menu.getHarga()));
            }
        }
        System.out.println("Minuman:");
        // Menggunakan for-each loop untuk menampilkan menu minuman
        for (Menu menu : daftarMenu) {
            if (menu.getKategori().equalsIgnoreCase("Minuman")) {
                System.out.println(String.format("  [%d] %-20s Rp %,8.0f", menu.getId(), menu.getNama(), menu.getHarga()));
            }
        }
        System.out.println("-----------------------------------");
    }

    // Method untuk menghitung total biaya dan mencetak struk
    private static void hitungDanCetakStruk(List<PesananItem> pesananPelanggan) {
        double subTotal = 0;
        int jumlahMinumanKeseluruhan = 0;
        Menu minumanTermurahUntukGratis = null;

        System.out.println("\n===================================");
        System.out.println("         STRUK PEMBAYARAN");
        System.out.println("         Dapoer Makassar");
        System.out.println("===================================");
        System.out.println("Tanggal: " + LocalDate.now());
        System.out.println("Waktu  : " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("-----------------------------------");
        System.out.println("Item                    Qty    Harga       Total");
        System.out.println("-----------------------------------");

        // Menggunakan for-each loop untuk memproses setiap item pesanan
        for (PesananItem item : pesananPelanggan) {
            subTotal += item.getTotalHargaItem();
            System.out.printf("%-24s %-5d Rp %,8.0f Rp %,8.0f\n",
                    item.getMenu().getNama(),
                    item.getJumlah(),
                    item.getMenu().getHarga(),
                    item.getTotalHargaItem());

            // Menghitung jumlah total minuman dan mencari minuman termurah
            if (item.getMenu().getKategori().equalsIgnoreCase("Minuman")) {
                jumlahMinumanKeseluruhan += item.getJumlah();
                // If nested untuk menentukan minuman termurah dari semua minuman yang dipesan
                if (minumanTermurahUntukGratis == null || item.getMenu().getHarga() < minumanTermurahUntukGratis.getHarga()) {
                    minumanTermurahUntukGratis = item.getMenu();
                }
            }
        }

        System.out.println("-----------------------------------");
        System.out.printf("Subtotal              : Rp %,12.0f\n", subTotal);

        final double PAJAK_PERSEN = 0.10; // Keyword final untuk konstanta pajak
        final double BIAYA_LAYANAN = 20000; // Keyword final untuk konstanta biaya layanan

        double pajak = subTotal * PAJAK_PERSEN;
        double biayaLayanan = BIAYA_LAYANAN;
        double totalSebelumDiskonPenawaran = subTotal + pajak + biayaLayanan;
        double diskon = 0;
        double potonganGratisSatu = 0;
        String infoDiskon = "";
        String infoPenawaran = "";

        // Skenario Keputusan: Diskon 10%
        // if-else if structure
        if (totalSebelumDiskonPenawaran > 100000) {
            diskon = totalSebelumDiskonPenawaran * 0.10;
            infoDiskon = "Diskon 10% (> Rp 100.000)";
            System.out.println("\n--- Skenario Keputusan ---");
            System.out.println("  * Total pesanan di atas Rp 100.000, memenuhi syarat diskon 10%.");
            System.out.println("--------------------------");
        } else if (subTotal > 50000) { // Cek untuk penawaran Beli 1 Gratis 1
            // Skenario Keputusan: Beli 1 Gratis 1 Minuman (nested if)
            if (jumlahMinumanKeseluruhan >= 2 && minumanTermurahUntukGratis != null) {
                // Asumsi: gratis 1 minuman termurah dari pesanan, jika ada minimal 2 minuman total
                // Perlu mencari item pesanan mana yang memiliki minuman termurah ini agar bisa dikurangi.
                // Jika ada lebih dari 1 jumlah minuman termurah, cukup mengurangi 1 saja.
                potonganGratisSatu = minumanTermurahUntukGratis.getHarga();
                infoPenawaran = "Beli 1 Gratis 1 Minuman (" + minumanTermurahUntukGratis.getNama() + ")";

                System.out.println("\n--- Skenario Keputusan ---");
                System.out.println("  * Total pesanan di atas Rp 50.000.");
                System.out.println("  * Terdapat " + jumlahMinumanKeseluruhan + " minuman dipesan. Memenuhi syarat penawaran beli 1 gratis 1.");
                System.out.println("  * Minuman " + minumanTermurahUntukGratis.getNama() + " akan digratiskan.");
                System.out.println("--------------------------");
            } else {
                System.out.println("\n--- Skenario Keputusan ---");
                System.out.println("  * Total pesanan di atas Rp 50.000, namun tidak memenuhi syarat penawaran beli 1 gratis 1 (misal: hanya ada " + jumlahMinumanKeseluruhan + " minuman).");
                System.out.println("--------------------------");
            }
        } else {
            System.out.println("\n--- Skenario Keputusan ---");
            System.out.println("  * Total pesanan di bawah Rp 50.000, tidak ada diskon atau penawaran khusus.");
            System.out.println("--------------------------");
        }


        double totalAkhir = totalSebelumDiskonPenawaran - diskon - potonganGratisSatu;

        System.out.printf("Pajak (%d%%)          : Rp %,12.0f\n", (int)(PAJAK_PERSEN * 100), pajak);
        System.out.printf("Biaya Pelayanan       : Rp %,12.0f\n", biayaLayanan);
        System.out.printf("Total (sebelum diskon): Rp %,12.0f\n", totalSebelumDiskonPenawaran);

        // if statement untuk menampilkan informasi diskon/penawaran jika ada
        if (diskon > 0) {
            System.out.printf("%-22s: Rp %,12.0f\n", infoDiskon, diskon);
        }
        if (potonganGratisSatu > 0) {
            System.out.printf("%-22s: Rp %,12.0f\n", infoPenawaran, potonganGratisSatu);
        }

        System.out.println("-----------------------------------");
        System.out.printf("TOTAL AKHIR           : Rp %,12.0f\n", totalAkhir);
        System.out.println("===================================\n");
        System.out.println("Terima kasih atas kunjungan Anda!");
    }

    // --- Bagian Manajemen Menu ---
    private static void menuManajemen() {
        boolean kembali = false;
        // while loop untuk menu manajemen
        while (!kembali) {
            System.out.println("\n=== Manajemen Menu Dapoer Makassar ===");
            System.out.println("1. Tambah Menu Baru");
            System.out.println("2. Ubah Harga Menu");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Tampilkan Semua Menu");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih opsi: ");
            String pilihan = scanner.nextLine();

            // switch case untuk navigasi menu manajemen
            switch (pilihan) {
                case "1":
                    tambahMenuBaru();
                    break;
                case "2":
                    ubahHargaMenu();
                    break;
                case "3":
                    hapusMenu();
                    break;
                case "4":
                    tampilkanDaftarMenu();
                    break;
                case "5":
                    kembali = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    // Method untuk menambah menu baru
    private static void tambahMenuBaru() {
        System.out.println("\n--- Tambah Menu Baru ---");
        System.out.print("Nama Menu: ");
        String nama = scanner.nextLine();

        double harga = 0;
        boolean hargaValid = false;
        // while loop untuk validasi input harga
        while (!hargaValid) {
            try {
                System.out.print("Harga Menu: ");
                harga = Double.parseDouble(scanner.nextLine());
                // if-else untuk validasi harga positif
                if (harga <= 0) {
                    System.out.println("Harga harus lebih dari 0.");
                } else {
                    hargaValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Harga tidak valid. Harap masukkan angka.");
            }
        }

        String kategori = "";
        boolean kategoriValid = false;
        // while loop untuk validasi input kategori
        while (!kategoriValid) {
            System.out.print("Kategori (Makanan/Minuman): ");
            kategori = scanner.nextLine();
            // if-else untuk validasi kategori
            if (kategori.equalsIgnoreCase("Makanan") || kategori.equalsIgnoreCase("Minuman")) {
                kategoriValid = true;
            } else {
                System.out.println("Kategori tidak valid. Harap masukkan 'Makanan' atau 'Minuman'.");
            }
        }

        Menu menuBaru = new Menu(nextMenuId++, nama, harga, kategori);
        tambahMenu(menuBaru); // Memanggil method tambahMenu untuk menambahkan objek Menu baru
        System.out.println("Menu '" + nama + "' berhasil ditambahkan!");
    }

    // Method untuk mengubah harga menu
    private static void ubahHargaMenu() {
        System.out.println("\n--- Ubah Harga Menu ---");
        // if statement untuk memeriksa jika daftar menu kosong
        if (daftarMenu.isEmpty()) {
            System.out.println("Daftar menu kosong. Tidak ada yang bisa diubah.");
            return;
        }

        tampilkanDaftarMenu();
        int menuId = -1;
        Menu menuToUpdate = null;

        // do-while loop untuk input ID menu dan validasi
        do {
            try {
                System.out.print("Masukkan ID menu yang ingin diubah harganya: ");
                menuId = Integer.parseInt(scanner.nextLine());
                // for-each loop untuk mencari menu berdasarkan ID
                for (Menu menu : daftarMenu) {
                    if (menu.getId() == menuId) {
                        menuToUpdate = menu;
                        break;
                    }
                }
                // if statement untuk memberi tahu jika ID tidak ditemukan
                if (menuToUpdate == null) {
                    System.out.println("ID menu tidak ditemukan. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan ID menu berupa angka.");
            }
        } while (menuToUpdate == null);


        System.out.println("Menu terpilih: " + menuToUpdate.getNama() + " (Harga saat ini: Rp " + String.format("%,.0f", menuToUpdate.getHarga()) + ")");

        double hargaBaru = 0;
        boolean hargaValid = false;
        // while loop untuk validasi input harga baru
        while (!hargaValid) {
            try {
                System.out.print("Masukkan harga baru untuk " + menuToUpdate.getNama() + ": ");
                hargaBaru = Double.parseDouble(scanner.nextLine());
                // if-else untuk validasi harga positif
                if (hargaBaru <= 0) {
                    System.out.println("Harga harus lebih dari 0.");
                } else {
                    hargaValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Harga tidak valid. Harap masukkan angka.");
            }
        }

        System.out.print("Konfirmasi perubahan harga '" + menuToUpdate.getNama() + "' menjadi Rp " + String.format("%,.0f", hargaBaru) + "? (Ya/Tidak): ");
        String konfirmasi = scanner.nextLine();

        // if-else structure untuk konfirmasi perubahan
        if (konfirmasi.equalsIgnoreCase("Ya")) {
            menuToUpdate.setHarga(hargaBaru); // Memanggil setter method
            System.out.println("Harga menu '" + menuToUpdate.getNama() + "' berhasil diubah.");
        } else {
            System.out.println("Perubahan harga dibatalkan.");
        }
    }

    // Method untuk menghapus menu
    private static void hapusMenu() {
        System.out.println("\n--- Hapus Menu ---");
        // if statement untuk memeriksa jika daftar menu kosong
        if (daftarMenu.isEmpty()) {
            System.out.println("Daftar menu kosong. Tidak ada yang bisa dihapus.");
            return;
        }

        tampilkanDaftarMenu();
        int menuId = -1;
        Menu menuToDelete = null;

        // while loop untuk input ID menu dan validasi
        while (menuToDelete == null) {
            try {
                System.out.print("Masukkan ID menu yang ingin dihapus: ");
                menuId = Integer.parseInt(scanner.nextLine());
                // for-each loop untuk mencari menu berdasarkan ID
                for (Menu menu : daftarMenu) {
                    if (menu.getId() == menuId) {
                        menuToDelete = menu;
                        break;
                    }
                }
                // if statement untuk memberi tahu jika ID tidak ditemukan
                if (menuToDelete == null) {
                    System.out.println("ID menu tidak ditemukan. Silakan coba lagi.");
                }
            } catch(NumberFormatException e){
                System.out.println("Input tidak valid. Harap masukkan ID berupa angka.");
            }
        }

        System.out.print("Konfirmasi penghapusan menu" + menuToDelete.getNama() + "?(Ya/Tidak):");
        String konfirmasi = scanner.nextLine();

        //struktur if-else untul konfirmasi penghapusan
        if(konfirmasi.equalsIgnoreCase("Ya")){
            daftarMenu.remove(menuToDelete); //Menghapus objek Menu dari ArrayList
            System.out.println("Menu" + menuToDelete.getNama() + "berhasil dihapus.");
        } else {
            System.out.println("Penghapusan menu dibatalkan.");
        }
    }
}
