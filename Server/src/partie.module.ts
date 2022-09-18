import { Module } from '@nestjs/common';
import { PartieController } from './partie.controller';
import { PartieService } from './partie.service';

@Module({
  imports: [],
  controllers: [PartieController],
  providers: [PartieService],
})
export class PartieModule {}
