package edu.prz.psieszko.health.application;

import edu.prz.psieszko.health.application.HealthCardApplicationService.CreateHealthCardCommand;
import edu.prz.psieszko.health.domain.HealthCard;
import edu.prz.psieszko.health.domain.Medicine;
import edu.prz.psieszko.health.domain.Vaccination;
import edu.prz.psieszko.health.domain.Veterinarian;
import edu.prz.psieszko.health.domain.VeterinaryVisit;
import edu.prz.psieszko.shared.identity.DogId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health-cards")
@Tag(name = "Health Cards")
public class HealthCardController {

    private final HealthCardApplicationService healthCardApplicationService;

    public HealthCardController(HealthCardApplicationService healthCardApplicationService) {
        this.healthCardApplicationService = healthCardApplicationService;
    }

    @PostMapping
    public ResponseEntity<HealthCardResponse> createHealthCard(
            @Valid @RequestBody CreateHealthCardRequest request
    ) {
        HealthCard healthCard = healthCardApplicationService.createHealthCard(request.toCommand());

        return ResponseEntity
                .created(URI.create("/api/health-cards/" + healthCard.getId()))
                .body(HealthCardResponse.from(healthCard));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthCardResponse> getHealthCard(@PathVariable Long id) {
        return ResponseEntity.ok(HealthCardResponse.from(healthCardApplicationService.getHealthCard(id)));
    }

    @GetMapping("/dog/{dogId}")
    public ResponseEntity<HealthCardResponse> getHealthCardByDogId(@PathVariable Long dogId) {
        return ResponseEntity.ok(HealthCardResponse.from(
                healthCardApplicationService.getHealthCardByDogId(new DogId(dogId))
        ));
    }

    @PostMapping("/{id}/veterinary-visits")
    public ResponseEntity<HealthCardResponse> registerVeterinaryVisit(
            @PathVariable Long id,
            @Valid @RequestBody VeterinaryVisitRequest request
    ) {
        return ResponseEntity.ok(HealthCardResponse.from(
                healthCardApplicationService.registerVeterinaryVisit(id, request.toVeterinaryVisit())
        ));
    }

    @PatchMapping("/{id}/vaccinations")
    public ResponseEntity<HealthCardResponse> replaceVaccinations(
            @PathVariable Long id,
            @Valid @RequestBody VaccinationsRequest request
    ) {
        return ResponseEntity.ok(HealthCardResponse.from(
                healthCardApplicationService.replaceVaccinations(id, request.toVaccinations())
        ));
    }

    @PatchMapping("/{id}/medicines")
    public ResponseEntity<HealthCardResponse> replaceMedicines(
            @PathVariable Long id,
            @Valid @RequestBody MedicinesRequest request
    ) {
        return ResponseEntity.ok(HealthCardResponse.from(
                healthCardApplicationService.replaceMedicines(id, request.toMedicines())
        ));
    }

    public record CreateHealthCardRequest(@NotNull @Positive Long dogId) {

        CreateHealthCardCommand toCommand() {
            return new CreateHealthCardCommand(new DogId(dogId));
        }
    }

    public record VeterinaryVisitRequest(
            @Valid @NotNull VeterinarianRequest veterinarian,
            @NotNull LocalDate visitDate,
            @NotBlank String description,
            String recommendations
    ) {

        VeterinaryVisit toVeterinaryVisit() {
            return new VeterinaryVisit(
                    veterinarian.toVeterinarian(),
                    visitDate,
                    description,
                    recommendations
            );
        }
    }

    public record VeterinarianRequest(
            @NotBlank String name,
            String licenseNumber
    ) {

        Veterinarian toVeterinarian() {
            return new Veterinarian(name, licenseNumber);
        }
    }

    public record VaccinationsRequest(Set<@Valid VaccinationRequest> vaccinations) {

        Set<Vaccination> toVaccinations() {
            if (vaccinations == null) {
                return Set.of();
            }
            return vaccinations.stream()
                    .map(VaccinationRequest::toVaccination)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    public record VaccinationRequest(
            @NotBlank String name,
            @NotNull LocalDate vaccinationDate,
            LocalDate nextVaccinationDate
    ) {

        Vaccination toVaccination() {
            return new Vaccination(name, vaccinationDate, nextVaccinationDate);
        }
    }

    public record MedicinesRequest(Set<@Valid MedicineRequest> medicines) {

        Set<Medicine> toMedicines() {
            if (medicines == null) {
                return Set.of();
            }
            return medicines.stream()
                    .map(MedicineRequest::toMedicine)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    public record MedicineRequest(
            @NotBlank String name,
            @NotBlank String dosage,
            @NotNull LocalDate startDate,
            LocalDate endDate
    ) {

        Medicine toMedicine() {
            return new Medicine(name, dosage, startDate, endDate);
        }
    }

    public record HealthCardResponse(
            Long id,
            Long dogId,
            Set<VeterinaryVisitResponse> veterinaryVisits,
            Set<VaccinationResponse> vaccinations,
            Set<MedicineResponse> medicines
    ) {

        static HealthCardResponse from(HealthCard healthCard) {
            return new HealthCardResponse(
                    healthCard.getId(),
                    healthCard.getDogId().id(),
                    healthCard.getVeterinaryVisits()
                            .stream()
                            .map(VeterinaryVisitResponse::from)
                            .collect(Collectors.toCollection(LinkedHashSet::new)),
                    healthCard.getVaccinations()
                            .stream()
                            .map(VaccinationResponse::from)
                            .collect(Collectors.toCollection(LinkedHashSet::new)),
                    healthCard.getMedicines()
                            .stream()
                            .map(MedicineResponse::from)
                            .collect(Collectors.toCollection(LinkedHashSet::new))
            );
        }
    }

    public record VeterinaryVisitResponse(
            VeterinarianResponse veterinarian,
            LocalDate visitDate,
            String description,
            String recommendations
    ) {

        static VeterinaryVisitResponse from(VeterinaryVisit veterinaryVisit) {
            return new VeterinaryVisitResponse(
                    VeterinarianResponse.from(veterinaryVisit.getVeterinarian()),
                    veterinaryVisit.getVisitDate(),
                    veterinaryVisit.getDescription(),
                    veterinaryVisit.getRecommendations()
            );
        }
    }

    public record VeterinarianResponse(String name, String licenseNumber) {

        static VeterinarianResponse from(Veterinarian veterinarian) {
            return new VeterinarianResponse(veterinarian.getName(), veterinarian.getLicenseNumber());
        }
    }

    public record VaccinationResponse(
            String name,
            LocalDate vaccinationDate,
            LocalDate nextVaccinationDate
    ) {

        static VaccinationResponse from(Vaccination vaccination) {
            return new VaccinationResponse(
                    vaccination.getName(),
                    vaccination.getVaccinationDate(),
                    vaccination.getNextVaccinationDate()
            );
        }
    }

    public record MedicineResponse(
            String name,
            String dosage,
            LocalDate startDate,
            LocalDate endDate
    ) {

        static MedicineResponse from(Medicine medicine) {
            return new MedicineResponse(
                    medicine.getName(),
                    medicine.getDosage(),
                    medicine.getStartDate(),
                    medicine.getEndDate()
            );
        }
    }
}
