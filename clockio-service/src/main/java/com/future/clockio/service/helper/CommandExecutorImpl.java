package com.future.clockio.service.helper;

import com.future.clockio.command.Command;
import com.future.clockio.service.core.CommandExecutorService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutorImpl implements CommandExecutorService, ApplicationContextAware {

  private ApplicationContext context;

  @Override
  public <REQUEST, RESPONSE> RESPONSE executeCommand(Class<? extends Command<REQUEST, RESPONSE>> commandClass, REQUEST request) {
    return this.context.getBean(commandClass).execute(request);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }
}
