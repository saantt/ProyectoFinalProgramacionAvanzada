package co.edu.uniquindio.proyectofinal.proyecto.util;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.InformeGeneradoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteInformeDTO;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Import the missing EstadisticasInformeDTO class
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EstadisticasInformeDTO;

public class PDFGenerator {
    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 18, Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.HELVETICA, 14, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.HELVETICA, 12, Font.NORMAL);
    private static final Font SMALL_FONT = new Font(Font.HELVETICA, 10, Font.NORMAL);

    public static byte[] generarInformePDF(InformeGeneradoDTO informe) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Título
            Paragraph title = new Paragraph(informe.tituloInforme(), TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Subtítulo
            Paragraph subtitle = new Paragraph(informe.periodo(), SUBTITLE_FONT);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(20);
            document.add(subtitle);

            // Estadísticas
            document.add(crearTablaEstadisticas(informe.estadisticas()));
            document.add(Chunk.NEWLINE);

            // Tabla de reportes
            document.add(crearTablaReportes(informe.reportes()));

            // Pie de página
            Paragraph footer = new Paragraph(
                    "Generado el: " + informe.fechaGeneracion().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                    SMALL_FONT);
            footer.setAlignment(Element.ALIGN_RIGHT);
            document.add(footer);

        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private static PdfPTable crearTablaEstadisticas(EstadisticasInformeDTO estadisticas) {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        agregarCelda(table, "Total Reportes", Color.LIGHT_GRAY);
        agregarCelda(table, String.valueOf(estadisticas.totalReportes()), Color.WHITE);

        agregarCelda(table, "Reportes Importantes", Color.LIGHT_GRAY);
        agregarCelda(table, String.valueOf(estadisticas.reportesImportantes()), Color.WHITE);

        // Agregar estadísticas por categoría
        estadisticas.reportesPorCategoria().forEach((categoria, cantidad) -> {
            agregarCelda(table, "Categoría " + categoria, Color.LIGHT_GRAY);
            agregarCelda(table, cantidad.toString(), Color.WHITE);
        });

        return table;
    }

    private static PdfPTable crearTablaReportes(List<ReporteInformeDTO> reportes) {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);

        // Encabezados
        String[] headers = { "Título", "Descripción", "Fecha", "Categoría", "Estado" };
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, SUBTITLE_FONT));
            cell.setBackgroundColor(new Color(70, 130, 180));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Datos
        for (ReporteInformeDTO reporte : reportes) {
            table.addCell(crearCelda(reporte.titulo()));
            table.addCell(crearCelda(reporte.descripcion()));
            table.addCell(crearCelda(
                    reporte.fecha().format(DateTimeFormatter.ISO_LOCAL_DATE)));
            table.addCell(crearCelda(reporte.nombreCategoria()));
            table.addCell(crearCelda(reporte.estado()));
        }

        return table;
    }

    private static PdfPCell crearCelda(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, NORMAL_FONT));
        cell.setPadding(5);
        return cell;
    }

    private static void agregarCelda(PdfPTable table, String texto, Color color) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, NORMAL_FONT));
        cell.setBackgroundColor(color);
        cell.setPadding(5);
        table.addCell(cell);
    }
}