package no.hiof.set.gruppe.model;

public interface IArrangementSortering {
    int compareName(Arrangement comparingArr, boolean Ascending);
    int compareDate(Arrangement comparingArr, boolean Ascending);
    int compareParticipants(Arrangement comparingArr, boolean Ascending);
    int compareSport(Arrangement comparingArr, boolean Ascending);
}
