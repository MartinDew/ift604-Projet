import { NestFactory } from '@nestjs/core';
import { PartieModule } from './partie.module';

async function bootstrap() {
  const app = await NestFactory.create(PartieModule);
  await app.listen(3000);
}
bootstrap();
