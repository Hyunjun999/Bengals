package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(EnsembleRepository ensembleRepository) {
        this.ensembleRepository = ensembleRepository;
    }

    public EnsembleDTO findEnsemble(String state) {
        Ensemble ensemble = ensembleRepository.findByState(state);
        OpportunityDistrictResDTO opportunityDistrictResDTO = new OpportunityDistrictResDTO();
        OpportunityDistrictRaceResDTO smd = new OpportunityDistrictRaceResDTO();
        OpportunityDistrictRaceResDTO mmd = new OpportunityDistrictRaceResDTO();

        processOpportunityDistrictsSMD(ensemble, smd, "blk");
        processOpportunityDistrictsSMD(ensemble, smd, "asn");
        processOpportunityDistrictsSMD(ensemble, smd, "hsp");
        processOpportunityDistrictsSMD(ensemble, smd, "non_wht");
        processOpportunityDistrictsMMD(ensemble, mmd, "blk");
        processOpportunityDistrictsMMD(ensemble, mmd, "asn");
        processOpportunityDistrictsMMD(ensemble, mmd, "hsp");
        processOpportunityDistrictsMMD(ensemble, mmd, "non_wht");

        opportunityDistrictResDTO.setSMD(smd);
        opportunityDistrictResDTO.setMMD(mmd);

        OpportunityRepResDTO opportunityRepResDTO = new OpportunityRepResDTO();
        OpportunityRepRaceResDTO op_rep_smd = new OpportunityRepRaceResDTO();
        OpportunityRepRaceResDTO op_rep_mmd = new OpportunityRepRaceResDTO();

        processOpportunityRepSMD(ensemble, op_rep_smd, "blk");
        processOpportunityRepSMD(ensemble, op_rep_smd, "asn");
        processOpportunityRepSMD(ensemble, op_rep_smd, "hsp");
        processOpportunityRepSMD(ensemble, op_rep_smd, "non_wht");
        processOpportunityRepMMD(ensemble, op_rep_mmd, "blk");
        processOpportunityRepMMD(ensemble, op_rep_mmd, "asn");
        processOpportunityRepMMD(ensemble, op_rep_mmd, "hsp");
        processOpportunityRepMMD(ensemble, op_rep_mmd, "non_wht");

        opportunityRepResDTO.setSMD(op_rep_smd);
        opportunityRepResDTO.setMMD(op_rep_mmd);

        EnsembleDTO ensembleDTO = EnsembleDTO.builder()
                .state(ensemble.getState())
                .box_whisker(ensemble.getBox_whisker())
                .vote_seats(ensemble.getVote_seats())
                .party_splits_bar(ensemble.getParty_splits_bar())
                .op_district_bar(opportunityDistrictResDTO)
                .op_representatives_bar(opportunityRepResDTO)
                .build();

        return ensembleDTO;
    }

    public void processOpportunityRepSMD(Ensemble ensemble, OpportunityRepRaceResDTO op_rep_smd, String race) {
        List<OpportunityRepReqCom> smdRace = null;
        if (race.equals("blk")) {
            smdRace = ensemble.getOp_representatives_bar().getSMD().getBlk();
        } else if (race.equals("asn")) {
            smdRace = ensemble.getOp_representatives_bar().getSMD().getAsn();
        } else if (race.equals("hsp")) {
            smdRace = ensemble.getOp_representatives_bar().getSMD().getHsp();
        } else if (race.equals("non_wht")) {
            smdRace = ensemble.getOp_representatives_bar().getSMD().getNon_wht();
        }
        List<OpportunityRepResCom> newSmdRace = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < smdRace.size(); i++) {
            if (!map.containsKey(smdRace.get(i).getOp_representatives())) {
                map.put(smdRace.get(i).getOp_representatives(), 1);
            } else {
                map.put(smdRace.get(i).getOp_representatives(),
                        map.get(smdRace.get(i).getOp_representatives()) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            OpportunityRepResCom newRaceCom = new OpportunityRepResCom();
            newRaceCom.setName(next.getKey());
            newRaceCom.setOp_representatives(next.getValue());
            newSmdRace.add(newRaceCom);
        }
        if (race.equals("blk")) {
            op_rep_smd.setBlk(newSmdRace);
        } else if (race.equals("asn")) {
            op_rep_smd.setAsn(newSmdRace);
        } else if (race.equals("hsp")) {
            op_rep_smd.setHsp(newSmdRace);
        } else if (race.equals("non_wht")) {
            op_rep_smd.setNon_wht(newSmdRace);
        }
    }
    public void processOpportunityRepMMD(Ensemble ensemble, OpportunityRepRaceResDTO op_rep_mmd, String race) {
        List<OpportunityRepReqCom> mmdRace = null;
        if (race.equals("blk")) {
            mmdRace = ensemble.getOp_representatives_bar().getMMD().getBlk();
        } else if (race.equals("asn")) {
            mmdRace = ensemble.getOp_representatives_bar().getMMD().getAsn();
        } else if (race.equals("hsp")) {
            mmdRace = ensemble.getOp_representatives_bar().getMMD().getHsp();
        } else if (race.equals("non_wht")) {
            mmdRace = ensemble.getOp_representatives_bar().getMMD().getNon_wht();
        }
        List<OpportunityRepResCom> newMmdRace = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < mmdRace.size(); i++) {
            if (!map.containsKey(mmdRace.get(i).getOp_representatives())) {
                map.put(mmdRace.get(i).getOp_representatives(), 1);
            } else {
                map.put(mmdRace.get(i).getOp_representatives(),
                        map.get(mmdRace.get(i).getOp_representatives()) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            OpportunityRepResCom newRaceCom = new OpportunityRepResCom();
            newRaceCom.setName(next.getKey());
            newRaceCom.setOp_representatives(next.getValue());
            newMmdRace.add(newRaceCom);
        }
        if (race.equals("blk")) {
            op_rep_mmd.setBlk(newMmdRace);
        } else if (race.equals("asn")) {
            op_rep_mmd.setAsn(newMmdRace);
        } else if (race.equals("hsp")) {
            op_rep_mmd.setHsp(newMmdRace);
        } else if (race.equals("non_wht")) {
            op_rep_mmd.setNon_wht(newMmdRace);
        }
    }


    public void processOpportunityDistrictsSMD(Ensemble ensemble, OpportunityDistrictRaceResDTO smd, String race) {
        List<OpportunityDistrictReqCom> smdRace = null;
        if (race.equals("blk")) {
            smdRace = ensemble.getOp_district_bar().getSMD().getBlk();
        } else if (race.equals("asn")) {
            smdRace = ensemble.getOp_district_bar().getSMD().getAsn();
        } else if (race.equals("hsp")) {
            smdRace = ensemble.getOp_district_bar().getSMD().getHsp();
        } else if (race.equals("non_wht")) {
            smdRace = ensemble.getOp_district_bar().getSMD().getNon_wht();
        }
        List<OpportunityDistrictResCom> newSmdRace = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < smdRace.size(); i++) {
            if (!map.containsKey(smdRace.get(i).getOp_districts())) {
                map.put(smdRace.get(i).getOp_districts(), 1);
            } else {
                map.put(smdRace.get(i).getOp_districts(), map.get(smdRace.get(i).getOp_districts()) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            OpportunityDistrictResCom newRaceCom = new OpportunityDistrictResCom();
            newRaceCom.setName(next.getKey());
            newRaceCom.setOp_districts(next.getValue());
            newSmdRace.add(newRaceCom);
        }
        if (race.equals("blk")) {
            smd.setBlk(newSmdRace);
        } else if (race.equals("asn")) {
            smd.setAsn(newSmdRace);
        } else if (race.equals("hsp")) {
            smd.setHsp(newSmdRace);
        } else if (race.equals("non_wht")) {
            smd.setNon_wht(newSmdRace);
        }
    }

    public void processOpportunityDistrictsMMD(Ensemble ensemble, OpportunityDistrictRaceResDTO mmd, String race) {
        List<OpportunityDistrictReqCom> mmdRace = null;
        if (race.equals("blk")) {
            mmdRace = ensemble.getOp_district_bar().getMMD().getBlk();
        } else if (race.equals("asn")) {
            mmdRace = ensemble.getOp_district_bar().getMMD().getAsn();
        } else if (race.equals("hsp")) {
            mmdRace = ensemble.getOp_district_bar().getMMD().getHsp();
        } else if (race.equals("non_wht")) {
            mmdRace = ensemble.getOp_district_bar().getMMD().getNon_wht();
        }
        List<OpportunityDistrictResCom> newMmdRace = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < mmdRace.size(); i++) {
            if (!map.containsKey(mmdRace.get(i).getOp_districts())) {
                map.put(mmdRace.get(i).getOp_districts(), 1);
            } else {
                map.put(mmdRace.get(i).getOp_districts(), map.get(mmdRace.get(i).getOp_districts()) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            OpportunityDistrictResCom newRaceCom = new OpportunityDistrictResCom();
            newRaceCom.setName(next.getKey());
            newRaceCom.setOp_districts(next.getValue());
            newMmdRace.add(newRaceCom);
        }
        if (race.equals("blk")) {
            mmd.setBlk(newMmdRace);
        } else if (race.equals("asn")) {
            mmd.setAsn(newMmdRace);
        } else if (race.equals("hsp")) {
            mmd.setHsp(newMmdRace);
        } else if (race.equals("non_wht")) {
            mmd.setNon_wht(newMmdRace);
        }
    }


}