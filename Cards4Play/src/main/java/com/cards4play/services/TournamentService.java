package com.cards4play.services;

import com.cards4play.models.*;
import com.cards4play.persistence.DataStore;

import java.util.List;

public class TournamentService {

    private final AppState state;

    public TournamentService(AppState state) {
        this.state = state;
    }

    // --- CRUD Torneo (ADMIN) ---

    public Tournament createTournament(String id, String name, String date, int capacity) {
        if (state.findTournamentById(id) != null)
            throw new IllegalArgumentException("Ya existe un torneo con ID: " + id);
        if (capacity <= 0)
            throw new IllegalArgumentException("La capacidad debe ser mayor a 0");
        Tournament t = new Tournament(id, name, date, capacity);
        state.getTournaments().add(t);
        DataStore.save(state);
        return t;
    }

    public List<Tournament> getAllTournaments() {
        return state.getTournaments();
    }

    public Tournament getTournamentById(String id) {
        Tournament t = state.findTournamentById(id);
        if (t == null) throw new IllegalArgumentException("Torneo no encontrado: " + id);
        return t;
    }

    public Tournament updateTournament(String id, String name, String date, Integer capacity) {
        Tournament t = getTournamentById(id);
        if (name != null) t.setName(name);
        if (date != null) t.setDate(date);
        if (capacity != null) {
            if (capacity < t.getClientIds().size())
                throw new IllegalArgumentException("La nueva capacidad no puede ser menor al número actual de inscritos");
            t.setCapacity(capacity);
        }
        DataStore.save(state);
        return t;
    }

    public void deleteTournament(String id) {
        Tournament t = getTournamentById(id);
        state.getTournaments().remove(t);
        DataStore.save(state);
    }

    // --- Inscripción (CLIENT) ---

    public Tournament registerClient(String tournamentId, String clientId) {
        Tournament t = getTournamentById(tournamentId);
        Client client = state.findClientById(clientId);
        if (client == null)
            throw new IllegalArgumentException("Cliente no encontrado: " + clientId);
        if (t.isClientRegistered(clientId))
            throw new IllegalStateException("El cliente ya está inscrito en este torneo");
        if (!t.hasCapacity())
            throw new IllegalStateException("El torneo no tiene cupos disponibles (máximo: " + t.getCapacity() + ")");

        t.getClientIds().add(clientId);
        DataStore.save(state);
        return t;
    }

    public void unregisterClient(String tournamentId, String clientId) {
        Tournament t = getTournamentById(tournamentId);
        if (!t.isClientRegistered(clientId))
            throw new IllegalStateException("El cliente no está inscrito en este torneo");
        t.getClientIds().remove(clientId);
        DataStore.save(state);
    }

    public List<String> getTournamentParticipants(String tournamentId) {
        return getTournamentById(tournamentId).getClientIds();
    }
}
