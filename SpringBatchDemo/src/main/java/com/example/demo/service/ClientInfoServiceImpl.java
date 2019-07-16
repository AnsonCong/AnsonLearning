package com.example.demo.service;

import com.example.demo.domain.Users;

/**
 * @author Anson
 * @date 2019/6/9
 */
public class ClientInfoServiceImpl implements ClientInfoService {
    @Autowired(name = "updateClientInfoJob")
    private Job updateClientInfoJob;

    @Override
    public void updateUser(Users u) {
    }

    public boolean triggerUploadClientInfoCsvJob(String fileName, String updateBy) {
        JobExecution jobExecution = null;
        try {
            jobExecution = jobExecution.run(updateClientInfoJob,new JobParameters()
            .addString("fileName",fileName)
            .addString("updatedBy",updateBy)
            .addString("executionTime",new Data())
            .toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ExitStatus.COMPLETED.equals(jobExecution.getExitStatus());
    }
}
