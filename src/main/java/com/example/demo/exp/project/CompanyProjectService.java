package com.example.demo.exp.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyProjectService {

    private final CompanyProjectRepository projectRepository;

    @Autowired
    public CompanyProjectService(CompanyProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public CompanyProjectResponse getCompanyProjects(String userId) throws Exception {
        CompanyProject project = projectRepository.findByUserId(userId);

        if (project == null) {
            throw new Exception("No project data found for userId: " + userId);
        }

        // 응답 객체 생성
        CompanyProjectResponse response = new CompanyProjectResponse();
        response.setProjectName(project.getProjectName());
        response.setRewardExp(project.getRewardExp());
        return response;
    }
}
