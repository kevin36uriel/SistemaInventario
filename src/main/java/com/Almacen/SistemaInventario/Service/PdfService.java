package com.Almacen.SistemaInventario.Service;

import com.Almacen.SistemaInventario.DTO.EmpleadoDTO;
import com.Almacen.SistemaInventario.DTO.ProductoDTO;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.*;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {

    public byte[] generarBajaProductoPdf(ProductoDTO producto, EmpleadoDTO empleado, String motivo) {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            // ====== Título y subtítulo
            try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 16);
                cs.newLineAtOffset(72, 720);
                cs.showText("Formato de Baja / Eliminación de Producto");
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 9);
                cs.newLineAtOffset(72, 705);
                cs.showText("Formulario editable. Puede modificar los campos tras descargar.");
                cs.endText();
            }

            // ====== AcroForm con recursos y apariencia por defecto
            PDAcroForm acroForm = new PDAcroForm(doc);
            acroForm.setNeedAppearances(false); // generaremos apariencia nosotros

            // Registrar recursos (la fuente 'Helv' que usamos en el DA)
            PDResources dr = new PDResources();
            dr.put(COSName.getPDFName("Helv"), PDType1Font.HELVETICA);
            acroForm.setDefaultResources(dr);

            // Apariencia por defecto de los campos (fuente + tamaño + color)
            acroForm.setDefaultAppearance("/Helv 11 Tf 0 g");
            doc.getDocumentCatalog().setAcroForm(acroForm);

            // ====== Layout
            float x = 72, y = 660, step = 56, labelW = 160, fieldW = 360, h = 20;

            // ====== Campos
            crearLabel(page, doc, "Producto (Nombre):", x, y + 22);
            crearCampo(acroForm, page, "producto_nombre",
                    x + labelW, y, fieldW, h, str(producto != null ? producto.getNombre() : ""));

            crearLabel(page, doc, "Código:", x, y - step + 22);
            crearCampo(acroForm, page, "producto_codigo",
                    x + labelW, y - step, fieldW, h, str(producto != null ? producto.getCodigo() : ""));

            crearLabel(page, doc, "Motivo de baja:", x, y - 2*step + 22);
            // Campo multilínea para motivos largos (aumentamos la altura)
            PDTextField fMotivo = crearCampo(acroForm, page, "motivo",
                    x + labelW, y - 2*step, fieldW, h * 2.5f, str(motivo));
            fMotivo.setMultiline(true);

            String nombreEmpleado = empleado != null
                    ? (str(empleado.getNombre()) + " " + str(empleado.getApellidos()))
                    : "";
            crearLabel(page, doc, "Empleado que realiza:", x, y - 3*step + 22);
            crearCampo(acroForm, page, "empleado_nombre",
                    x + labelW, y - 3*step, fieldW, h, nombreEmpleado);

            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            crearLabel(page, doc, "Fecha / Hora:", x, y - 4*step + 22);
            crearCampo(acroForm, page, "fecha_hora",
                    x + labelW, y - 4*step, fieldW, h, fecha);

            // ====== Forzar apariencia de widgets (no solo NeedAppearances)
            // Refresca la apariencia de todos los campos del formulario
            acroForm.refreshAppearances();

            doc.save(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error generando PDF de baja de producto", e);
        }
    }

    private String str(String s) { return s == null ? "" : s; }

    private void crearLabel(PDPage page, PDDocument doc, String text, float x, float y) throws IOException {
        try (PDPageContentStream cs = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true)) {
            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 11);
            cs.newLineAtOffset(x, y);
            cs.showText(text);
            cs.endText();
        }
    }

    private PDTextField crearCampo(PDAcroForm form, PDPage page, String name,
                                   float x, float y, float w, float h, String value) throws IOException {
        PDTextField field = new PDTextField(form);
        field.setPartialName(name);
        field.setDefaultAppearance("/Helv 11 Tf 0 g");
        form.getFields().add(field);

        PDAnnotationWidget widget = new PDAnnotationWidget();
        widget.setRectangle(new PDRectangle(x, y, w, h));
        widget.setPage(page);
        // dibujar un borde ligero ayuda a “ver” el campo
        PDBorderStyleDictionary border = new PDBorderStyleDictionary();
        border.setWidth(0.75f);
        widget.setBorderStyle(border);

        page.getAnnotations().add(widget);
        field.setWidgets(List.of(widget));

        // Asignar valor
        field.setValue(value != null ? value : "");
        return field;
    }
}
