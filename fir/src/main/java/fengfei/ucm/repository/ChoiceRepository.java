package fengfei.ucm.repository;


import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Choice;

public interface ChoiceRepository extends UnitNames {
    int addChoice(final Choice choice) throws Exception;

    int deleteChoice(final long idPhoto) throws Exception;

    int deleteChoiceByAt(final int at) throws Exception;
}
