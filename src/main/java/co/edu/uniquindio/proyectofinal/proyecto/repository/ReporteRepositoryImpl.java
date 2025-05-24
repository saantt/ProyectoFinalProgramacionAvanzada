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
            UbicacionSector sector = obtenerCoordenadasSector(sectorNombre);
            query.addCriteria(
                    Criteria.where("ubicacion.latitud").gte(sector.getLatitudMin()).lte(sector.getLatitudMax())
                            .and("ubicacion.longitud").gte(sector.getLongitudMin()).lte(sector.getLongitudMax()));
        }

        return mongoTemplate.find(query, Reporte.class);
    }

    private UbicacionSector obtenerCoordenadasSector(String nombreSector) {
        Map<String, UbicacionSector> sectores = Map.of(
                "norte", new UbicacionSector(4.5500, -75.6800, 4.5700, -75.6600),
                "sur", new UbicacionSector(4.5100, -75.7100, 4.5300, -75.6900),
                "centro", new UbicacionSector(4.5300, -75.6800, 4.5500, -75.6600));
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