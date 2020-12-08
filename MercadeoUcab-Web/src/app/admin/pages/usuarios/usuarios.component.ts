import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {MatTableDataSource} from '@angular/material/table';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { Usuario } from '@models/usuario';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { UpdateUserDialogComponent } from '../../components/dialogs/update-user-dialog/update-user-dialog.component';
import { DeleteUserDialogComponent } from '../../components/dialogs/delete-user-dialog/delete-user-dialog.component';
import { UsuarioService } from '@core/services/usuario/usuario.service';

class UserModel {
  _id:number;
}

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css'],
  providers:[MatDatepickerModule]
})
export class UsuariosComponent implements OnInit {
  //CONTROL DE ESTADO DEL COMPONENTE
  op:string;
  searchState:string;//U.I,D
  users: UserModel[] = [];

  //COLUMNAS DE TABLA DE RESULTADOS
  displayedColumns: string[] = ['id','selector','ops'];
  columnsToDisplay: string[] = this.displayedColumns.slice();

  //INDICE DE USUARIO SELECCIONADO
  userSelection:number = 0;

  //LISTA DE USUARIOS DEVUELTOS EN BÚSQUEDA
  dataSource : MatTableDataSource<UserModel>;

  //FORMULARIOS
  updForm;
  searchForm;
  searchModel:Usuario;
  addForm;
  opStatus:string;//S,P,D
  userRole:string = "";
  setTipoUsuario(tipo:string){
    this.userRole=tipo;
  }

   constructor(
     private modalService: NgbModal,
     private formBuilder: FormBuilder,
     private _userService: UsuarioService,
    ) {
    this.updForm = this.formBuilder.group({
      nombre:'',
    });
    this.searchForm = this.formBuilder.group({
      nombre:'',
      apellido:'',
      rol:'',//SELECT
      estado:'',//SELECT
      activo:true,//CHECKBOX O SELECT
      creado_el:'',//DATE TO STRING
      modificado_el:''//DATE TO STRING
    })
   }

  getUsers(){
    this._userService.getUsers().subscribe(
      response => {
        console.log(response);
      },
      error => {
        console.log(error);
      }
    )
  }
  ngAfterViewInit() {}

  ngOnInit(): void {
    this.setOperation('');
    this.searchState="U";
    this.getUsers();
  }
  @ViewChild('updUser') private updComponent:UpdateUserDialogComponent;
  async openUpdModal() {
    return await this.updComponent.open();
  }
  @ViewChild('delUser') private delComponent:DeleteUserDialogComponent;
  async openDelModal() {
    return await this.delComponent.open();
  }

  serviceInvoke(role:string){
    //MEDIO PARA DETERMINAR SERVICIO A INVOCAR SEGUN FORMULARIO DE CREACION DE USUARIO
    switch(role){
      case 'Admnistrador':
        break;
      case 'Analista':
        break;
      case 'Cliente':
        break;
      case 'Encuestado':
        break;
      default:
        break;
    }
    this.opStatus="P";
    setTimeout(()=>{
      this.opStatus="D";
    },3000);
  }
  selectUser(id: number){
    if(id === this.userSelection){
      this.userSelection = 0;
    }
    else{
      this.userSelection=id;
    }
  }
  isSelected(id: number):boolean{
    if(id === this.userSelection){
      return true;
    }
    return false;
  }
  invokeSearch(){
    //console.log(this.searchForm.value);
    this.searchState="P";
    setTimeout(()=>{
      for (let i = 0; i < Math.floor(Math.random()*(100-1)+1); i++) {
        this.users.push({_id:i+1});
      }
      this.dataSource = new MatTableDataSource<UserModel>(this.users);
      this.searchState="D";
    },3000);
  }
  setOperation(chOp:string){
    this.op=chOp;
    if(chOp !== ''){
      this.searchState="I";
      this.opStatus="S";
      this.setTipoUsuario('');
    }
    else{
      this.searchState="U";
    }
  }
  doSearch(){
    this.searchState="I";
  }
}
