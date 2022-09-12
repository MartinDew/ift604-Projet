import { Controller, Get } from '@nestjs/common';
import { getuid } from 'process';
import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return "scatmandmsajdasbfusgdiu!"
  }
}
