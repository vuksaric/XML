package services.campaignservices.service.implementation;

import org.springframework.stereotype.Service;
import services.campaignservices.model.TargetGroup;
import services.campaignservices.repository.TargetGroupRepository;
import services.campaignservices.service.ITargetGroupService;

@Service
public class TargetGroupService implements ITargetGroupService {

    private final TargetGroupRepository targetGroupRepository;

    public TargetGroupService(TargetGroupRepository targetGroupRepository) {
        this.targetGroupRepository = targetGroupRepository;
    }

    @Override
    public TargetGroup getById(int id) {
        return targetGroupRepository.findOneById(id);
    }
}
