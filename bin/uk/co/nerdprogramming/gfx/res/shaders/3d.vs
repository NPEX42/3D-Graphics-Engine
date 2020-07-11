#version 330 core
in vec4 a_Position;
in vec2 a_UV;
out vec2 v_UV;
void main() {
	gl_Position = a_Position;
	v_UV = a_UV;
}