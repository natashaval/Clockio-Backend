package com.future.clockio.command;

public interface Command<REQUEST, RESPONSE> {
  RESPONSE execute(REQUEST request);
}
