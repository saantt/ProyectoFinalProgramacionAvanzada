
package co.edu.uniquindio.proyectofinal.proyecto.util;

import org.bson.types.ObjectId;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EditarReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    // @Mapping(target = "categoriaId", expression = "java(new
    // ObjectId(dto.getCategoriaId()))")

    @Mapping(target = "ubicacion", source = "ubicacion")

    Reporte toDocument(ReporteCreacionDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clienteId", ignore = true)
    @Mapping(target = "categoriaId", ignore = true)
    @Mapping(target = "estadoActual", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "contadorImportante", ignore = true)
    @Mapping(target = "historial", ignore = true)
    void EditarReporteDTO(EditarReporteDTO dto, @MappingTarget Reporte reporte);

    // Conversión personalizada de String a ObjectId
    default ObjectId map(String value) {
        return new ObjectId(value);
    }

    // ✅ Conversión personalizada de ObjectId a String (para toDTO)
    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    // Conversión personalizada para ubicación
    default Ubicacion mapUbicacionDTO(UbicacionDTO dto) {
        return new Ubicacion(dto.latitud(), dto.longitud());
    }

    // Conversión de documento a DTO
    ReporteDTO toDTO(Reporte reporte);
}
