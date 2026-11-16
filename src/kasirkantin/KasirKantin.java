package kasirkantin;

import java.awt.*;
import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class KasirKantin extends JFrame {

    private JTextField txtNama;
    private JTextField txtHarga;
    private JTextField txtJumlah;
    private JTextField txtPembayaran;

    private JLabel lblTotal;
    private JLabel lblKembalian;

    private JButton btnHitung;
    private JButton btnBayar;
    private JButton btnReset;

    private long totalHarga = 0;

    public KasirKantin() {

        setTitle("Aplikasi Kasir Kantin Kampus");
        setSize(550, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelUtama = new JPanel(new BorderLayout(10, 10));
        panelUtama.setBackground(new Color(245, 247, 250));
        panelUtama.setBorder(
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        // JUDUL APLIKASI
        JLabel judul = new JLabel(
            "KASIR KANTIN KAMPUS", JLabel.CENTER
        );

        judul.setFont(new Font("Arial", Font.BOLD, 24));
        judul.setForeground(new Color(30, 70, 120));

        JLabel subJudul = new JLabel(
            "Aplikasi Transaksi Penjualan", JLabel.CENTER
        );

        subJudul.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel panelJudul = new JPanel(new GridLayout(2, 1));
        panelJudul.setOpaque(false);
        panelJudul.add(judul);
        panelJudul.add(subJudul);

        panelUtama.add(panelJudul, BorderLayout.NORTH);

        // FORM INPUT
        JPanel panelForm = new JPanel(
            new GridLayout(4, 2, 12, 15)
        );

        panelForm.setBackground(Color.WHITE);

        panelForm.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    new Color(220, 220, 220)
                ),
                BorderFactory.createEmptyBorder(
                    20, 20, 20, 20
                )
            )
        );

        txtNama = new JTextField();
        txtHarga = new JTextField();
        txtJumlah = new JTextField();
        txtPembayaran = new JTextField();

        tambahForm(
            panelForm, "Nama Makanan/Minuman", txtNama
        );

        tambahForm(
            panelForm, "Harga Satuan (Rp)", txtHarga
        );

        tambahForm(
            panelForm, "Jumlah Pembelian", txtJumlah
        );

        tambahForm(
            panelForm, "Uang Pembayaran (Rp)", txtPembayaran
        );

        // HASIL TRANSAKSI
        JPanel panelHasil = new JPanel(
            new GridLayout(2, 2, 10, 12)
        );

        panelHasil.setBackground(Color.WHITE);

        panelHasil.setBorder(
            BorderFactory.createTitledBorder(
                "Hasil Transaksi"
            )
        );

        lblTotal = new JLabel("Rp0");
        lblKembalian = new JLabel("Rp0");

        lblTotal.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        lblKembalian.setFont(
            new Font("Arial", Font.BOLD, 16)
        );

        panelHasil.add(new JLabel("Total Harga:"));
        panelHasil.add(lblTotal);

        panelHasil.add(new JLabel("Kembalian:"));
        panelHasil.add(lblKembalian);

        JPanel panelTengah = new JPanel(
            new BorderLayout(10, 15)
        );

        panelTengah.setOpaque(false);
        panelTengah.add(panelForm, BorderLayout.CENTER);
        panelTengah.add(panelHasil, BorderLayout.SOUTH);

        panelUtama.add(panelTengah, BorderLayout.CENTER);

        // TOMBOL
        btnHitung = new JButton("HITUNG");
        btnBayar = new JButton("BAYAR");
        btnReset = new JButton("RESET");

        btnHitung.setBackground(
            new Color(52, 152, 219)
        );

        btnBayar.setBackground(
            new Color(39, 174, 96)
        );

        btnReset.setBackground(
            new Color(231, 76, 60)
        );

        btnHitung.setForeground(Color.WHITE);
        btnBayar.setForeground(Color.WHITE);
        btnReset.setForeground(Color.WHITE);

        JPanel panelTombol = new JPanel(
            new GridLayout(1, 3, 10, 10)
        );

        panelTombol.setOpaque(false);

        panelTombol.add(btnHitung);
        panelTombol.add(btnBayar);
        panelTombol.add(btnReset);

        panelUtama.add(panelTombol, BorderLayout.SOUTH);

        add(panelUtama);

        // EVENT HANDLING
        btnHitung.addActionListener(e -> hitungTotal());
        btnBayar.addActionListener(e -> prosesBayar());
        btnReset.addActionListener(e -> resetForm());
    }

    // MENAMBAHKAN LABEL DAN INPUT
    private void tambahForm(
        JPanel panel, String teks, JTextField input
    ) {

        JLabel label = new JLabel(teks);
        label.setFont(
            new Font("Arial", Font.PLAIN, 13)
        );

        input.setFont(
            new Font("Arial", Font.PLAIN, 14)
        );

        panel.add(label);
        panel.add(input);
    }

    // FORMAT RUPIAH
    private String rupiah(long nilai) {

        NumberFormat format = NumberFormat.getInstance(
            new Locale("id", "ID")
        );

        return "Rp" + format.format(nilai);
    }

    // VALIDASI ANGKA
    private long ambilAngka(
        JTextField field, String namaKolom
    ) {

        String nilai = field.getText().trim();

        if (nilai.isEmpty()) {
            throw new IllegalArgumentException(
                namaKolom + " wajib diisi!"
            );
        }

        if (!nilai.matches("-?[0-9]+")) {
            throw new IllegalArgumentException(
                namaKolom + " harus berupa angka!"
            );
        }

        return Long.parseLong(nilai);
    }

    // VALIDASI NAMA BARANG
    private boolean validasiNama() {

        if (txtNama.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Nama makanan atau minuman wajib diisi!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );

            txtNama.requestFocus();
            return false;
        }

        return true;
    }

    // TOMBOL HITUNG
    private void hitungTotal() {

        try {

            if (!validasiNama()) {
                return;
            }

            long harga = ambilAngka(
                txtHarga, "Harga satuan"
            );

            long jumlah = ambilAngka(
                txtJumlah, "Jumlah pembelian"
            );

            if (harga < 0) {
                throw new IllegalArgumentException(
                    "Harga tidak boleh negatif!"
                );
            }

            if (jumlah <= 0) {
                throw new IllegalArgumentException(
                    "Jumlah pembelian harus lebih dari nol!"
                );
            }

            totalHarga = Math.multiplyExact(
                harga, jumlah
            );

            lblTotal.setText(rupiah(totalHarga));
            lblKembalian.setText("Belum dibayar");

            JOptionPane.showMessageDialog(
                this,
                "Total harga berhasil dihitung!\nTotal: "
                + rupiah(totalHarga),
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Angka terlalu besar atau tidak valid!",
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (ArithmeticException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Total harga melebihi batas perhitungan!",
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // TOMBOL BAYAR
    private void prosesBayar() {

        try {

            if (!validasiNama()) {
                return;
            }

            long harga = ambilAngka(
                txtHarga, "Harga satuan"
            );

            long jumlah = ambilAngka(
                txtJumlah, "Jumlah pembelian"
            );

            long pembayaran = ambilAngka(
                txtPembayaran, "Uang pembayaran"
            );

            if (harga < 0 || pembayaran < 0) {
                throw new IllegalArgumentException(
                    "Harga dan pembayaran tidak boleh negatif!"
                );
            }

            if (jumlah <= 0) {
                throw new IllegalArgumentException(
                    "Jumlah pembelian harus lebih dari nol!"
                );
            }

            totalHarga = Math.multiplyExact(
                harga, jumlah
            );

            lblTotal.setText(rupiah(totalHarga));

            if (pembayaran < totalHarga) {

                long kekurangan =
                    totalHarga - pembayaran;

                lblKembalian.setText(
                    "Kurang " + rupiah(kekurangan)
                );

                JOptionPane.showMessageDialog(
                    this,
                    "Pembayaran kurang sebesar "
                    + rupiah(kekurangan),
                    "Pembayaran Kurang",
                    JOptionPane.WARNING_MESSAGE
                );

            } else {

                long kembalian =
                    pembayaran - totalHarga;

                lblKembalian.setText(
                    rupiah(kembalian)
                );

                JOptionPane.showMessageDialog(
                    this,
                    "Pembayaran berhasil!\n"
                    + "Total: " + rupiah(totalHarga)
                    + "\nPembayaran: " + rupiah(pembayaran)
                    + "\nKembalian: " + rupiah(kembalian),
                    "Transaksi Berhasil",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Angka terlalu besar atau tidak valid!",
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (ArithmeticException ex) {

            JOptionPane.showMessageDialog(
                this,
                "Total harga melebihi batas perhitungan!",
                "Kesalahan",
                JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // TOMBOL RESET
    private void resetForm() {

        txtNama.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
        txtPembayaran.setText("");

        lblTotal.setText("Rp0");
        lblKembalian.setText("Rp0");

        totalHarga = 0;

        txtNama.requestFocus();
    }

    // PROGRAM UTAMA
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new KasirKantin().setVisible(true);
        });
    }
}
