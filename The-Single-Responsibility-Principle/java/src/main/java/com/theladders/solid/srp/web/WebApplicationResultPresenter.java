package com.theladders.solid.srp.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.theladders.solid.srp.Result;
import com.theladders.solid.srp.apply.ApplicationResultPresenter;

public class WebApplicationResultPresenter implements ApplicationResultPresenter<Result>
{

  @Override
  public Result invalidJob(int jobId)
  {
    Map<String, Object> model = new HashMap<>();
    model.put("jobId", jobId);

    return new Result("invalidJob", model);
  }

  @Override
  public Result applicationFailed()
  {
    List<String> errList = new ArrayList<>();
    errList.add("We could not process your application.");
    return new Result("error", Collections.<String, Object> emptyMap(), errList);
  }

  @Override
  public Result needsToCompleteResume(int jobId,
                                      String jobTitle)
  {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("jobId", jobId);
    model.put("jobTitle", jobTitle);

    return new Result("completeResumePlease", model);
  }

  @Override
  public Result success(int jobId,
                        String jobTitle)
  {
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("jobId", jobId);
    model.put("jobTitle", jobTitle);

    return new Result("success", model);
  }

}
