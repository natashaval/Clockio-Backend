package com.future.clockio.service.core;

import com.future.clockio.command.Command;

public interface CommandExecutorService {
  <REQUEST, RESPONSE> RESPONSE executeCommand(Class<? extends Command<REQUEST,RESPONSE>> commandClass, REQUEST request);
}
