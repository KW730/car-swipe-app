export class Car{
  constructor(
    public id: number,
    public make: string,
    public imageUrl: string,
    public productionYear: number,
    public power: string,
    public engine: string,
    public gearbox: string,
    public mileage: string,
    public fuel: string,
    public price: number
  ) {}
}
