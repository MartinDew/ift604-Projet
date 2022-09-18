import { Controller, Get, Param } from '@nestjs/common';
import { PartieService } from './partie.service';
import { Partie } from 'Partie';

@Controller("parties")
export class PartieController {
  constructor(private readonly appService: PartieService) {}

  @Get()
  getParties(): Partie[] {
    return this.appService.getAllParties()
  }

  @Get(":id_partie")
  getPartie(@Param() params): Partie[] {
    return this.appService.getPartie(params.id_partie)
  }
}
