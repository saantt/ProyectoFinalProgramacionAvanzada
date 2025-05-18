package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.springframework.data.geo.Point;

@Repository
@RequiredArgsConstructor
public class ReporteRepositoryImpl implements ReporteRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Reporte> findByFilters(String categoriaId, String sectorNombre,
            LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Query query = new Query();

        // 1. Filtro por categoría (EXACTO como en MongoDB Shell)
        if (categoriaId != null && !categoriaId.isEmpty()) {
            query.addCriteria(Criteria.where("categoriaId").is(new ObjectId(categoriaId)));
        }

        // 2. Filtro por fechas (usa UTC como en la BD)
        query.addCriteria(Criteria.where("fecha")
                .gte(fechaInicio.atZone(ZoneId.of("UTC")).toInstant())
                .lte(fechaFin.atZone(ZoneId.of("UTC")).toInstant()));

        // 3. Filtro por sector (ajusta los rangos como en la consulta manual)
        if (sectorNombre != null && !sectorNombre.isEmpty()) {
            query.addCriteria(
                    Criteria.where("ubicacion.latitud").gte(4.80).lte(4.90)
                            .and("ubicacion.longitud").gte(-75.80).lte(-75.65));
        }

        return mongoTemplate.find(query, Reporte.class);
    }

    private UbicacionSector obtenerCoordenadasSector(String nombreSector) {
        Map<String, UbicacionSector> sectores = Map.of(
                "norte", new UbicacionSector(4.80, -75.80, 4.90, -75.65), // Amplía el rango de longitud
                "sur", new UbicacionSector(4.70, -75.85, 4.80, -75.70),
                "centro", new UbicacionSector(4.75, -75.75, 4.85, -75.65));
        return sectores.getOrDefault(nombreSector.toLowerCase(), new UbicacionSector(0, 0, 0, 0));
    }

    @Getter
    @AllArgsConstructor
    private static class UbicacionSector {
        private final double latitudMin;
        private final double longitudMin;
        private final double latitudMax;
        private final double longitudMax;
    }
}